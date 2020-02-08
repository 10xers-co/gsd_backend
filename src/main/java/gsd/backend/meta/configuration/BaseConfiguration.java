package gsd.backend.meta.configuration;

import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BaseConfiguration extends Configuration {
    private SwaggerBundleConfiguration swaggerBundleConfiguration;
}
