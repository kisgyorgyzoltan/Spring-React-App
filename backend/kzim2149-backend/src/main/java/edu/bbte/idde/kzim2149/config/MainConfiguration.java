package edu.bbte.idde.kzim2149.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MainConfiguration {
    @JsonProperty("jdbc")
    private JdbcConfiguration jdbcConfiguration = new JdbcConfiguration();

    @JsonProperty("connectionPool")
    private ConnectionPoolConfiguration connectionPoolConfiguration = new ConnectionPoolConfiguration();

    public JdbcConfiguration getJdbcConfiguration() {
        return jdbcConfiguration;
    }

    public void setJdbcConfiguration(JdbcConfiguration jdbcConfiguration) {
        this.jdbcConfiguration = jdbcConfiguration;
    }

    public ConnectionPoolConfiguration getConnectionPoolConfiguration() {
        return connectionPoolConfiguration;
    }

    public void setConnectionPoolConfiguration(ConnectionPoolConfiguration connectionPoolConfiguration) {
        this.connectionPoolConfiguration = connectionPoolConfiguration;
    }

    @Override
    public String toString() {
        return "MainConfiguration{" + "jdbcConfiguration=" + jdbcConfiguration
                + ", connectionPoolConfiguration=" + connectionPoolConfiguration
                + '}';
    }
}
