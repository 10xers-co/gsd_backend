package gsd.backend.configuration;

import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DatabaseConfig extends HikariDatabaseConfig {
    private Map<String, Object> jpaPropertiesMap;

    private String persistenceXmlLocation;

    private String persistenceUnitName;
}

