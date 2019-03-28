package pl.com.itti.app.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan(basePackages = {"pl.com.itti.driver.app.model"})
@EnableJpaRepositories(basePackages = {"pl.com.itti.driver.app.repository"})
@EnableTransactionManagement
public class AppDbConfig {
}
