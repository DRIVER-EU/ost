package eu.fp7.driver.ost;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import eu.fp7.driver.ost.driver.util.CustomTimeDeserializer;
import eu.fp7.driver.ost.driver.util.CustomTimeSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.TimeZone;

@SpringBootApplication(exclude = {RepositoryRestMvcAutoConfiguration.class})
@EnableJpaRepositories("eu.fp7.driver.ost")
@EntityScan(basePackages = "eu.fp7.driver.ost")
@EnableScheduling
public class Application {

    public static String version = "00.00.16b";

    public static void main(String[] args) {
        // TODO migrate to Hibernate 5.2 & setting timezone via properties
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Locale.setDefault(new Locale("pl", "PL"));
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @Primary
    public ObjectMapper serializingObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(ZonedDateTime.class, new CustomTimeSerializer());
        javaTimeModule.addDeserializer(ZonedDateTime.class, new CustomTimeDeserializer());
        objectMapper.registerModule(javaTimeModule);
        objectMapper.registerModule(new Jdk8Module());
        return objectMapper;
    }
}