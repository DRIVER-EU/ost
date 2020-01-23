package pl.com.itti.app.core.security.security.config;

import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.web.http.CookieHttpSessionStrategy;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;



@Configuration
@EnableJdbcHttpSession(maxInactiveIntervalInSeconds = HttpSessionConfig.SESSION_TIMEOUT)
public class HttpSessionConfig {

    /**
     * The session timeout in seconds - set to 2 hours.
     */
    static final int SESSION_TIMEOUT = 7200;

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
        HttpSessionStrategy sessionStrategy;

        switch (securityProperties.getSession().getStrategy()) {
            case "cookie":
                DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
                cookieSerializer.setCookieName(securityProperties.getSession().getCookieName());
                cookieSerializer.setCookieMaxAge(securityProperties.getSession().getCookieMaxAge());

                CookieHttpSessionStrategy cookieSessionStrategy = new CookieHttpSessionStrategy();
                cookieSessionStrategy.setCookieSerializer(cookieSerializer);

                sessionStrategy = cookieSessionStrategy;
                break;
            case "header":
                HeaderHttpSessionStrategy headerSessionStrategy = new HeaderHttpSessionStrategy();
                headerSessionStrategy.setHeaderName(securityProperties.getSession().getHeaderName());

                sessionStrategy = headerSessionStrategy;
                break;
            default:
                throw new InvalidPropertyException(SecurityProperties.class, "session.strategy",
                        "Unknown strategy name: " + securityProperties.getSession().getStrategy());
        }

        return sessionStrategy;
    }
}
