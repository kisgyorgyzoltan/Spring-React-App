package edu.bbte.idde.kzim2149.dao.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.kzim2149.config.ConfigurationFactory;
import edu.bbte.idde.kzim2149.config.JdbcConfiguration;
import edu.bbte.idde.kzim2149.dao.*;
import edu.bbte.idde.kzim2149.model.PCPart;
import edu.bbte.idde.kzim2149.model.PrebuiltPC;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public class PrebuiltPCDao implements PrebuiltPCDaoI {
    private static final HikariDataSource dataSource = DataSourceManager.getDataSource();
    private static final PCPartsDaoI pcPartsDao = AbstractDaoFactory.getInstance().getPCPartsDao();

    public PrebuiltPCDao() {
        super();
        createTable();
    }

    boolean checkIfValidPCPart(PCPart pcPart) {
        if (pcPart.getName().isEmpty()) {
            return false;
        }
        if (pcPart.getProducer().isEmpty()) {
            return false;
        }
        if (pcPart.getType().isEmpty()) {
            return false;
        }
        if (pcPart.getPrice() < 0) {
            return false;
        }
        return pcPart.getWeight() >= 0;
    }

    boolean checkIfValidPrebuiltPC(PrebuiltPC prebuiltPC) {
        if (prebuiltPC.getPrice() <= 0) {
            return false;
        }

        return checkIfValidPCPart(prebuiltPC.getCpu()) && checkIfValidPCPart(prebuiltPC.getGpu())
                && checkIfValidPCPart(prebuiltPC.getRam()) && checkIfValidPCPart(prebuiltPC.getMotherboard())
                && checkIfValidPCPart(prebuiltPC.getPsu()) && checkIfValidPCPart(prebuiltPC.getStorage());
    }

    private Collection<PrebuiltPC> responseToCollection(ResultSet resultSet) {
        Collection<PrebuiltPC> prebuiltPCs = new ArrayList<>();
        try {
            while (resultSet.next()) {
                PrebuiltPC prebuiltPC = new PrebuiltPC();
                prebuiltPC.setId(resultSet.getLong("id"));
                prebuiltPC.setCpu(pcPartsDao.findById(resultSet.getLong("cpu")));
                prebuiltPC.setGpu(pcPartsDao.findById(resultSet.getLong("gpu")));
                prebuiltPC.setRam(pcPartsDao.findById(resultSet.getLong("ram")));
                prebuiltPC.setMotherboard(pcPartsDao.findById(resultSet.getLong("motherboard")));
                prebuiltPC.setPsu(pcPartsDao.findById(resultSet.getLong("psu")));
                prebuiltPC.setStorage(pcPartsDao.findById(resultSet.getLong("storage")));
                prebuiltPC.setPrice(resultSet.getInt("price"));
                if (checkIfValidPrebuiltPC(prebuiltPC)) {
                    prebuiltPCs.add(prebuiltPC);
                }
            }
        } catch (SQLException e) {
            log.error("SQL exception: " + e.getMessage());
            throw new RepoException(e);
        }
        return prebuiltPCs;
    }

    private PrebuiltPC responseToObject(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                PrebuiltPC prebuiltPC = new PrebuiltPC();
                prebuiltPC.setId(resultSet.getLong("id"));
                prebuiltPC.setCpu(pcPartsDao.findById(resultSet.getLong("cpu")));
                prebuiltPC.setGpu(pcPartsDao.findById(resultSet.getLong("gpu")));
                prebuiltPC.setRam(pcPartsDao.findById(resultSet.getLong("ram")));
                prebuiltPC.setMotherboard(pcPartsDao.findById(resultSet.getLong("motherboard")));
                prebuiltPC.setPsu(pcPartsDao.findById(resultSet.getLong("psu")));
                prebuiltPC.setStorage(pcPartsDao.findById(resultSet.getLong("storage")));
                prebuiltPC.setPrice(resultSet.getInt("price"));
                if (checkIfValidPrebuiltPC(prebuiltPC)) {
                    return prebuiltPC;
                }
                return null;
            }
        } catch (SQLException e) {
            log.error("SQL exception: " + e.getMessage());
            throw new RepoException(e);
        }
        return null;
    }

    private void createTable() {
        JdbcConfiguration jdbcConfiguration = ConfigurationFactory.getJdbcConfiguration();
        boolean create = jdbcConfiguration.getCreateTables();
        try (Connection connection = dataSource.getConnection()) {
            String tableName = "prebuiltpcs";
            boolean tableExists = DataSourceManager.checkIfTableExists(connection, tableName);
            if (create && !tableExists) {
                log.info("Creating table " + tableName);
                try (PreparedStatement preparedStatement = connection.prepareStatement(
                        "CREATE TABLE "
                                + tableName
                                + " ( id bigint not null auto_increment, cpu bigint, gpu bigint, ram bigint, "
                                + "motherboard bigint, psu bigint, storage bigint, price int, "
                                + "primary key (id) )")
                ) {
                    preparedStatement.executeUpdate();
                    // Inserting some data
                    // 1 prebuilt pc
                    insert(new PrebuiltPC(
                                    pcPartsDao.findById(1L),
                                    pcPartsDao.findById(2L),
                                    pcPartsDao.findById(3L),
                                    pcPartsDao.findById(4L),
                                    pcPartsDao.findById(5L),
                                    pcPartsDao.findById(6L)
                            )
                    );

                }
            }
        } catch (SQLException e) {
            log.error("SQL exception: " + e.getMessage());
            throw new RepoException(e);
        }
    }


    @Override
    public PrebuiltPC insert(PrebuiltPC entity) {
        createTable();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO prebuiltpcs "
                            + "(cpu, gpu, ram, motherboard, psu, storage, price) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?)")
            ) {
                preparedStatement.setLong(1, entity.getCpu().getId());
                preparedStatement.setLong(2, entity.getGpu().getId());
                preparedStatement.setLong(3, entity.getRam().getId());
                preparedStatement.setLong(4, entity.getMotherboard().getId());
                preparedStatement.setLong(5, entity.getPsu().getId());
                preparedStatement.setLong(6, entity.getStorage().getId());
                preparedStatement.setInt(7, entity.getPrice());
                preparedStatement.executeUpdate();
                log.info("Added: " + entity + " with id: " + entity.getId());
                return entity;
            }
        } catch (SQLException e) {
            log.error("SQL exception: " + e.getMessage());
            throw new RepoException(e);
        }
    }

    @Override
    public void delete(Long id) {
        createTable();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM prebuiltpcs WHERE id=?")
            ) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
                log.info("Deleted: " + id);
            }
        } catch (SQLException e) {
            log.error("SQL exception: " + e.getMessage());
            throw new RepoException(e);
        }
    }

    @Override
    public PrebuiltPC update(Long id, PrebuiltPC updatedEntity) {
        createTable();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE prebuiltpcs "
                            + "SET cpu=?, gpu=?, ram=?, motherboard=?, psu=?, storage=?,"
                            + " price=? WHERE id=?")
            ) {
                preparedStatement.setLong(1, updatedEntity.getCpu().getId());
                preparedStatement.setLong(2, updatedEntity.getGpu().getId());
                preparedStatement.setLong(3, updatedEntity.getRam().getId());
                preparedStatement.setLong(4, updatedEntity.getMotherboard().getId());
                preparedStatement.setLong(5, updatedEntity.getPsu().getId());
                preparedStatement.setLong(6, updatedEntity.getStorage().getId());
                preparedStatement.setInt(7, updatedEntity.getPrice());
                preparedStatement.setLong(8, id);
                preparedStatement.executeUpdate();
                log.info("Updated: " + updatedEntity + " with id: " + id);
                return updatedEntity;
            }
        } catch (SQLException e) {
            log.error("SQL exception: " + e.getMessage());
            throw new RepoException(e);
        }
    }

    @Override
    public Collection<PrebuiltPC> findAll() {
        createTable();
        Collection<PrebuiltPC> prebuiltPCs;
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM prebuiltpcs")
            ) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    prebuiltPCs = responseToCollection(resultSet);
                    log.info("Found all: " + prebuiltPCs);
                    return prebuiltPCs;
                }
            }
        } catch (SQLException e) {
            log.error("SQL exception: " + e.getMessage());
            throw new RepoException(e);
        }
    }

    @Override
    public PrebuiltPC findById(Long id) {
        createTable();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM prebuiltpcs WHERE id=?")
            ) {
                preparedStatement.setLong(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    PrebuiltPC prebuiltPC = responseToObject(resultSet);
                    log.info("Found: " + prebuiltPC + " with id: " + id);
                    return prebuiltPC;
                }
            }
        } catch (SQLException e) {
            log.error("SQL exception: " + e.getMessage());
            throw new RepoException(e);
        }
    }

    @Override
    public Collection<PrebuiltPC> findByPrice(Integer price) {
        createTable();
        Collection<PrebuiltPC> prebuiltPCs;
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM prebuiltpcs WHERE price=?")
            ) {
                preparedStatement.setInt(1, price);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    prebuiltPCs = responseToCollection(resultSet);
                    log.info("Found: " + prebuiltPCs + " with price: " + price);
                    return prebuiltPCs;
                }
            }
        } catch (SQLException e) {
            log.error("SQL exception: " + e.getMessage());
            throw new RepoException(e);
        }
    }
}
