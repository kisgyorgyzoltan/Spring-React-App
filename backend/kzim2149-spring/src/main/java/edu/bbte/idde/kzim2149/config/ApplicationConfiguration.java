package edu.bbte.idde.kzim2149.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Data
@Configuration
@Profile("prod")
public class ApplicationConfiguration {
    @Value("${createTables}")
    private Boolean createTables;

    @Value("${driverClass}")
    private String driverClass;

    @Value("${url}")
    private String url;

    @Value("${user}")
    private String user;

    @Value("${password}")
    private String password;

    @Value("${poolSize}")
    private Integer poolSize;

    @Value("${poolName}")
    private String poolName;
}
