package gsd.backend;

import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import org.activejpa.utils.OpenSessionInViewFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import gsd.backend.configuration.BackendConfiguration;
import gsd.backend.meta.BaseApplication;
import gsd.backend.registers.DataSourceRegister;
import gsd.backend.registers.ResourceRegister;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import minisu.dropwizard.interpolation.EnvironmentVariableInterpolationBundle;

public class GsdBackendApplication extends BaseApplication<BackendConfiguration> {
    private ManagedResource managedResource;

    public static void main(String[] args) throws Exception {
        new GsdBackendApplication().run(args);
    }

    @Override
    public void initialize(final Bootstrap<BackendConfiguration> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addBundle(new EnvironmentVariableInterpolationBundle());
        bootstrap.addBundle(new ViewBundle<>());
    }

    public void run(final BackendConfiguration configuration, final Environment environment) throws Exception {
        super.run(configuration, environment);
        registerOSIVFilter(environment);
        this.managedResource = new ManagedResource();
        final DataSourceRegister dataSourceRegister = new DataSourceRegister(managedResource);
        final ResourceRegister resourceRegister = new ResourceRegister();
        dataSourceRegister.register(environment, configuration);
        environment.jersey().register(MultiPartFeature.class);

    }

    private void registerOSIVFilter(Environment environment) {
        final Filter openSessionInViewFilter = new OpenSessionInViewFilter();
        final FilterRegistration.Dynamic filter = environment.servlets().addFilter("OSIVFilter", openSessionInViewFilter);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        final FilterRegistration.Dynamic adminFilter = environment.admin().addFilter("OSIVFilter", openSessionInViewFilter);
        adminFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }
}
