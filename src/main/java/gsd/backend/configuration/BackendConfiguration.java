package gsd.backend.configuration;

import java.util.List;
import gsd.backend.meta.configuration.BaseConfiguration;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BackendConfiguration extends BaseConfiguration {
    List<ApplicationConfig> applicationConfigs;

    private DatabaseConfig databaseConfig;

}
