package gsd.backend.meta;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jersey2.InstrumentedResourceMethodApplicationListener;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import gsd.backend.meta.configuration.BaseConfiguration;
import gsd.backend.meta.exception.GsdWebExceptionMapper;
import gsd.backend.meta.filters.CorsFilter;
import gsd.backend.meta.filters.RequestTracingFilter;
import gsd.backend.meta.resource.MetricsResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import lombok.Data;

@Data
public abstract class BaseApplication<T extends BaseConfiguration> extends Application<T> {
    private String accessControlAllowOrigin;

    private MetricRegistry metricRegistry;

    @Override
    public void initialize(final Bootstrap<T> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<T>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(final T configuration) {
                return configuration.getSwaggerBundleConfiguration();
            }
        });
        super.initialize(bootstrap);
        this.metricRegistry = bootstrap.getMetricRegistry();
    }

    public void run(T configuration, Environment environment) throws Exception {
        environment.getObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        environment.jersey().register(new CorsFilter(accessControlAllowOrigin));
        environment.jersey().register(RequestTracingFilter.class);
        environment.jersey().register(new MetricsResource(metricRegistry));
        environment.jersey().register(GsdWebExceptionMapper.class);
        environment.jersey().register(new InstrumentedResourceMethodApplicationListener(metricRegistry));
    }
}