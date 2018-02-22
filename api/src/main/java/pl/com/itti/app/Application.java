package pl.com.itti.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Locale;
import java.util.TimeZone;

@SpringBootApplication(exclude = {RepositoryRestMvcAutoConfiguration.class})
@EnableJpaRepositories("pl.com.itti")
@EntityScan(basePackages = "pl.com.itti")
@ComponentScan(basePackages = {"co.perpixel", "pl.com.itti"})
public class Application {

    public static void main(String[] args) {
        // TODO migrate to Hibernate 5.2 & setting timezone via properties
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Locale.setDefault(new Locale("pl", "PL"));
        SpringApplication.run(Application.class, args);
    }
}