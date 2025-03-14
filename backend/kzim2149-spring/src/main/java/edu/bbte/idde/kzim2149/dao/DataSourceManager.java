package edu.bbte.idde.kzim2149.dao;

import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.kzim2149.config.ApplicationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
@Profile("prod")
public class DataSourceManager {

    @Autowired
    private ApplicationConfiguration configuration;

    @Bean
    public DataSource buildDataSource() {

        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setJdbcUrl(configuration.getUrl());
        dataSource.setDriverClassName(configuration.getDriverClass());
        dataSource.setUsername(configuration.getUser());
        dataSource.setPassword(configuration.getPassword());
        dataSource.setMaximumPoolSize(configuration.getPoolSize());
        dataSource.setPoolName(configuration.getPoolName());

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
