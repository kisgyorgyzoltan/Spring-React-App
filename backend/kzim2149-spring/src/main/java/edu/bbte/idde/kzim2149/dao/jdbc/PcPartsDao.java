package edu.bbte.idde.kzim2149.dao.jdbc;

import edu.bbte.idde.kzim2149.config.ApplicationConfiguration;
import edu.bbte.idde.kzim2149.dao.DataSourceManager;
import edu.bbte.idde.kzim2149.dao.PcPartsDaoI;
import edu.bbte.idde.kzim2149.exception.RepoException;
import edu.bbte.idde.kzim2149.model.PcPart;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@Repository
@Profile("prod")
@Slf4j
public class PcPartsDao implements PcPartsDaoI {
    @Autowired
    private ApplicationConfiguration applicationConfiguration;
    @Autowired
    private DataSource dataSource;

    boolean checkIfValidPCPart(PcPart pcPart) {
        if (pcPart.getName() == null || pcPart.getName().isEmpty()) {
            return false;
        }
        if (pcPart.getProducer() == null || pcPart.getProducer().isEmpty()) {
            return false;
        }
        if (pcPart.getType() == null || pcPart.getType().isEmpty()) {
            return false;
        }
        if (pcPart.getPrice() < 0) {
            return false;
        }
        return pcPart.getWeight() >= 0;
    }

    private Collection<PcPart> responseToCollection(ResultSet resultSet) {
        Collection<PcPart> pcParts = new ArrayList<>();
        try {
            while (resultSet.next()) {
                PcPart pcPart = new PcPart();
                pcPart.setId(resultSet.getLong("id"));
                pcPart.setName(resultSet.getString("name"));
                pcPart.setProducer(resultSet.getString("producer"));
                pcPart.setType(resultSet.getString("type"));
                pcPart.setPrice(resultSet.getInt("price"));
                pcPart.setWeight(resultSet.getInt("weight"));
                if (checkIfValidPCPart(pcPart)) {
                    pcParts.add(pcPart);
                }
            }
        } catch (SQLException e) {
            log.error("SQL exception: " + e.getMessage());
            throw new RepoException(e);
        }
        return pcParts;
    }

    private PcPart responseToObject(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                PcPart pcPart = new PcPart();
                pcPart.setId(resultSet.getLong("id"));
                pcPart.setName(resultSet.getString("name"));
                pcPart.setProducer(resultSet.getString("producer"));
                pcPart.setType(resultSet.getString("type"));
                pcPart.setPrice(resultSet.getInt("price"));
                pcPart.setWeight(resultSet.getInt("weight"));
                if (checkIfValidPCPart(pcPart)) {
                    return pcPart;
                }
                return null;
            }
            return null;
        } catch (SQLException e) {
            log.error("SQL exception: " + e.getMessage());
            throw new RepoException(e);
        }
    }

    @PostConstruct
    public void createTable() {
        boolean create = applicationConfiguration.getCreateTables();
        try (Connection connection = dataSource.getConnection()) {
            String tableName = "pcparts";
            boolean tableExists = DataSourceManager.checkIfTableExists(connection, tableName);
            if (create && !tableExists) {
                log.info("Creating table " + tableName);
                try (PreparedStatement preparedStatement = connection.prepareStatement(
                        "CREATE TABLE "
                                + tableName
                                + " ( id bigint not null auto_increment, name varchar(255), "
                                + "producer varchar(255), type varchar(255), price int, weight int, "
                                + "primary key (id) )")
                ) {
                    log.error("Creating table");
                    preparedStatement.executeUpdate();
                    // Inserting some data
                    log.error("Inserting some data");
                    // 1 cpu
                    insert(new PcPart("Intel Core i7-10700K", "Intel", "CPU", 300, 0));
                    // 1 gpu
                    insert(new PcPart("Nvidia GeForce RTX 3080", "Nvidia", "GPU", 700, 0));
                    // 1 ram
                    insert(new PcPart("Corsair Vengeance RGB Pro 32GB", "Corsair", "RAM", 200, 0));
                    // 1 motherboard
                    insert(new PcPart("Asus ROG Strix Z490-E Gaming", "Asus", "Motherboard", 300, 0));
                    // 1 psu
                    insert(new PcPart("Corsair RM850x", "Corsair", "PSU", 150, 0));
                    // 1 storage
                    insert(new PcPart("Samsung 970 Evo Plus 1TB", "Samsung", "Storage", 150, 0));
                    log.error("Data inserted");
                }
            }
        } catch (SQLException e) {
            log.error("SQL exception: " + e.getMessage());
            throw new RepoException(e);
        }
    }

    @Override
    public PcPart insert(PcPart insertedModel) {
        log.info("Inserting: " + insertedModel);
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO pcparts (name, producer, type, price, weight) VALUES "
                            + "(?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS)
            ) {
                preparedStatement.setString(1, insertedModel.getName());
                preparedStatement.setString(2, insertedModel.getProducer());
                preparedStatement.setString(3, insertedModel.getType());
                preparedStatement.setInt(4, insertedModel.getPrice());
                preparedStatement.setInt(5, insertedModel.getWeight());
                int id = preparedStatement.executeUpdate();
                if (id > 0) {
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            insertedModel.setId(generatedKeys.getLong(1));
                            log.info("Inserted: " + insertedModel);
                            return insertedModel;
                        }
                    }
                }
                log.info("Failed to add: " + insertedModel);
                return null;
            }
        } catch (SQLException e) {
            log.error("SQL exception: " + e.getMessage());
            throw new RepoException(e);
        }
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting: " + id);
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM pcparts WHERE id=?")) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
                log.info("Removed: " + id);
            }
        } catch (SQLException e) {
            log.error("SQL exception: " + e.getMessage());
            throw new RepoException(e);
        }
    }

    @Override
    public PcPart update(Long id, PcPart updatedModel) {
        log.info("Updating: " + id + " with " + updatedModel);
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE pcparts SET name=?, producer=?, type=?, price=?, weight=? WHERE id=?")
            ) {
                preparedStatement.setString(1, updatedModel.getName());
                preparedStatement.setString(2, updatedModel.getProducer());
                preparedStatement.setString(3, updatedModel.getType());
                preparedStatement.setInt(4, updatedModel.getPrice());
                preparedStatement.setInt(5, updatedModel.getWeight());
                preparedStatement.setLong(6, id);
                preparedStatement.executeUpdate();
                log.info("Updated: " + updatedModel);
                return updatedModel;
            }
        } catch (SQLException e) {
            log.error("SQL exception: " + e.getMessage());
            throw new RepoException(e);
        }
    }

    @Override
    public Collection<PcPart> findAll() {
        log.info("Finding all");
        Collection<PcPart> pcParts;
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM pcparts")) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    pcParts = responseToCollection(resultSet);
                    log.info("Found all: " + pcParts);
                    return pcParts;
                }
            }
        } catch (SQLException e) {
            log.error("SQL exception: " + e.getMessage());
            throw new RepoException(e);
        }
    }

    @Override
    public PcPart findById(Long id) {
        log.info("Finding: " + id);
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM pcparts WHERE id=?")
            ) {
                preparedStatement.setLong(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    PcPart pcPart = responseToObject(resultSet);
                    log.info("Found: " + pcPart);
                    return pcPart;
                }
            }
        } catch (SQLException e) {
            log.error("SQL exception: " + e.getMessage());
            throw new RepoException(e);
        }
    }

    @Override
    public Collection<PcPart> findByName(String name) {
        log.info("Finding: " + name);
        Collection<PcPart> pcParts;
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM pcparts WHERE name=?")
            ) {
                preparedStatement.setString(1, name);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    pcParts = responseToCollection(resultSet);
                    log.info("Search failed. Not found: " + name);
                    return pcParts;
                }
            }
        } catch (SQLException e) {
            log.error("SQL exception: " + e.getMessage());
            throw new RepoException(e);
        }
    }
}
