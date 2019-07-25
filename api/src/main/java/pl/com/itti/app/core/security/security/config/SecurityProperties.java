package pl.com.itti.app.core.security.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security", ignoreUnknownFields = false)
public class SecurityProperties {

    private final Session session = new Session();

    public Session getSession() {
        return session;
    }

    public static class Session {

        private String strategy;

        private String cookieName;

        private int cookieMaxAge;

        private String headerName;

        public String getStrategy() {
            return strategy;
        }

        public void setStrategy(String strategy) {
            this.strategy = strategy;
        }

        public String getCookieName() {
            return cookieName;
        }

        public void setCookieName(String cookieName) {
            this.cookieName = cookieName;
        }

        public int getCookieMaxAge() {
            return cookieMaxAge;
        }

        public void setCookieMaxAge(int cookieMaxAge) {
            this.cookieMaxAge = cookieMaxAge;
        }

        public String getHeaderName() {
            return headerName;
        }

        public void setHeaderName(String headerName) {
            this.headerName = headerName;
        }
    }
}