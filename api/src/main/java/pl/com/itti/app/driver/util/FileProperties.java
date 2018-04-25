package pl.com.itti.app.driver.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "driver.upload")
@Getter
@Setter
public class FileProperties {

    private String imageDir;
    private String soundDir;
}
