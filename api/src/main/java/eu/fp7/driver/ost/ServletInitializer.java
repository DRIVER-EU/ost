package eu.fp7.driver.ost;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import java.util.Locale;
import java.util.TimeZone;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        // TODO migrate to Hibernate 5.2 & setting timezone via properties
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Locale.setDefault(new Locale("pl", "PL"));
        return application.sources(Application.class);
    }
}