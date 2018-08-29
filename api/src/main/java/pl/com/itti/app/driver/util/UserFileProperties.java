package pl.com.itti.app.driver.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "driver.user")
@Getter
@Setter
public class UserFileProperties {

    private String txtDir;
}
