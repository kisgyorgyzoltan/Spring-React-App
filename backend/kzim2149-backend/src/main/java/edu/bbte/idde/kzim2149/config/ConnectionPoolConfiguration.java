package edu.bbte.idde.kzim2149.config;

import lombok.Data;

@Data
public class ConnectionPoolConfiguration {
    private Integer poolSize = 1;
    private String poolName = "PCPartsPool";
}
