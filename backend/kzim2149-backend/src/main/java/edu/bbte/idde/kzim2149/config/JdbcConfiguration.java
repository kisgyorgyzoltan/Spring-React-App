package edu.bbte.idde.kzim2149.config;

import lombok.Data;

@Data
public class JdbcConfiguration {
    private Boolean createTables;
    private String driverClass;
    private String url;
    private String user;
    private String password;
}
