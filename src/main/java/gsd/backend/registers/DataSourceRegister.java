package gsd.backend.registers;

import javax.sql.DataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import gsd.backend.ManagedResource;
import gsd.backend.configuration.BackendConfiguration;
import gsd.backend.configuration.DatabaseConfig;
import gsd.backend.meta.healthcheck.DataSourceHealthCheck;
import io.dropwizard.setup.Environment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DataSourceRegister {
    private final ManagedResource managedResource;

    @Getter
    private DataSource dataSource;

    public void register(final Environment environment, final BackendConfiguration configuration) {
        dataSource = createDataSource(configuration.getDatabaseConfig());
        environment.healthChecks().register("My Sql Healthcheck", new DataSourceHealthCheck(dataSource));
        managedResource.initialize(createLocalContainerEntityManagerFactoryBean(dataSource, configuration.getDatabaseConfig()),
                configuration.getDatabaseConfig().getPersistenceUnitName());
    }

    private DataSource createDataSource(final DatabaseConfig databaseConfig) {
        final HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(databaseConfig.getDriverClass());
        dataSource.setJdbcUrl(databaseConfig.getUrl());
        dataSource.setUsername(databaseConfig.getUser());
        dataSource.setPassword(databaseConfig.getPassword());
        dataSource.setIdleTimeout(databaseConfig.getMaxIdleTime());
        dataSource.setConnectionTestQuery(databaseConfig.getTestStatement());
        dataSource.setConnectionInitSql(databaseConfig.getTestStatement());
        dataSource.setMinimumIdle(databaseConfig.getMinPoolSize());
        dataSource.setMaximumPoolSize(databaseConfig.getMaxPoolSize());
        dataSource.setIdleTimeout(databaseConfig.getIdleConnectionTestPeriod());
        return dataSource;
    }

    private LocalContainerEntityManagerFactoryBean createLocalContainerEntityManagerFactoryBean(final DataSource dataSource,
            final DatabaseConfig databaseConfig) {
        final LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource);
        localContainerEntityManagerFactoryBean.setPersistenceXmlLocation(databaseConfig.getPersistenceXmlLocation());
        localContainerEntityManagerFactoryBean.setPersistenceUnitName(databaseConfig.getPersistenceUnitName());
        localContainerEntityManagerFactoryBean.setJpaPropertyMap(databaseConfig.getJpaPropertiesMap());
        return localContainerEntityManagerFactoryBean;
    }
}
