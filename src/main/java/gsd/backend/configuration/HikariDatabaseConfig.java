package gsd.backend.configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HikariDatabaseConfig {
    private String driverClass;

    private String user;

    private String password;

    private String url;

    private int acquireIncrement;

    private int initialPoolSize;

    private int minPoolSize;

    private int maxPoolSize;

    private int maxIdleTime;

    private int maxStatements;

    private int idleConnectionTestPeriod;

    private String testStatement;
}
