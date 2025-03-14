package edu.bbte.idde.kzim2149.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class ConfigurationFactory {
    private static final String CONFIG_FILE = "/application";

    @Getter
    private static MainConfiguration mainConfiguration = new MainConfiguration();

    static {
        ObjectMapper objectMapper = new ObjectMapper();

        String configFilePath = System.getenv("CONFIG_FILE_PATH");
        configFilePath = configFilePath == null ? buildConfigFilePath() : buildConfigFilePath(configFilePath);

        try (InputStream inputStream = ConfigurationFactory.class.getResourceAsStream(configFilePath)) {
            mainConfiguration = objectMapper.readValue(inputStream, MainConfiguration.class);
            log.info("Read following configuration: {}", mainConfiguration);
        } catch (IOException e) {
            log.error("Error loading configuration", e);
        }
    }

    private static String buildConfigFilePath() {
        return buildConfigFilePath(CONFIG_FILE);
    }

    private static String buildConfigFilePath(String filePath) {
        String profile = System.getenv("PROFILE");
        if (profile == null) {
            log.info("Using profile: {}", "prod");
            return filePath + "-" + "prod" + ".json";
        }

        log.info("Using profile: {}", profile);
        return filePath + "-" + profile + ".json";
    }

    public static JdbcConfiguration getJdbcConfiguration() {
        return mainConfiguration.getJdbcConfiguration();
    }

    public static ConnectionPoolConfiguration getConnectionPoolConfiguration() {
        return mainConfiguration.getConnectionPoolConfiguration();
    }
}
