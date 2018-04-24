package pl.com.itti.app.driver.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "driver.upload")
public class FileProperties {

    private String imageDir;
    private String dzwiekDir; // TODO change this
}
