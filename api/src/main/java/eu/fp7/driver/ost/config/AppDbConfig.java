package eu.fp7.driver.ost.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan(basePackages = {"eu.fp7.driver.ost.core.persistence.db.model",
        "eu.fp7.driver.ost.core.security.security.model",
        "eu.fp7.driver.ost.driver.app.model"})
@EnableJpaRepositories(basePackages = {"eu.fp7.driver.ost.core.security.security.repository",
        "eu.fp7.driver.ost.driver.repository"})
@EnableTransactionManagement
public class AppDbConfig {
}
