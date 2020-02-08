package gsd.backend;

import org.activejpa.enhancer.ActiveJpaAgentLoader;
import org.activejpa.jpa.JPA;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import io.dropwizard.lifecycle.Managed;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ManagedResource implements Managed {
    private LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean;

    private String persistanceUnitName;

    public void initialize(final LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean, final String persistanceUnitName) {
        this.localContainerEntityManagerFactoryBean = localContainerEntityManagerFactoryBean;
        this.persistanceUnitName = persistanceUnitName;
    }

    public void start() throws Exception {
        log.info("Inializing Active JPA module");
        ActiveJpaAgentLoader.instance().loadAgent();
        log.info("Registering persistance context to ActiveJPA");
        this.localContainerEntityManagerFactoryBean.afterPropertiesSet();
        JPA.instance.addPersistenceUnit(persistanceUnitName, localContainerEntityManagerFactoryBean.getObject(), true);

    }

    public void stop() {
        log.info("Closing JPA instance");
        JPA.instance.close();

    }

}
