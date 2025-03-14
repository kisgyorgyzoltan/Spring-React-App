package edu.bbte.idde.kzim2149.dao;

import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.kzim2149.config.ConfigurationFactory;
import edu.bbte.idde.kzim2149.config.ConnectionPoolConfiguration;
import edu.bbte.idde.kzim2149.config.JdbcConfiguration;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSourceManager {
    private static HikariDataSource dataSource;

    public static synchronized HikariDataSource getDataSource() {
        if (dataSource == null) {
            dataSource = buildDataSource();
        }
        return dataSource;
    }

    private static HikariDataSource buildDataSource() {
        JdbcConfiguration jdbcConfiguration = ConfigurationFactory.getJdbcConfiguration();
        ConnectionPoolConfiguration connectionPoolConfiguration = ConfigurationFactory.getConnectionPoolConfiguration();

        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setJdbcUrl(jdbcConfiguration.getUrl());
        dataSource.setDriverClassName(jdbcConfiguration.getDriverClass());
        dataSource.setUsername(jdbcConfiguration.getUser());
        dataSource.setPassword(jdbcConfiguration.getPassword());
        dataSource.setMaximumPoolSize(connectionPoolConfiguration.getPoolSize());
        dataSource.setPoolName(connectionPoolConfiguration.getPoolName());

        return dataSource;
    }

    public static boolean checkIfTableExists(Connection connection, String tableName) {
        DatabaseMetaData metadata;
        try {
            metadata = connection.getMetaData();
            try (ResultSet resultSet = metadata.getTables(null, null, tableName, null)) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            return false;
        }
    }
}
