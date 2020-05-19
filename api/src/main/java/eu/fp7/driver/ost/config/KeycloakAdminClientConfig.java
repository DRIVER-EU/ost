package eu.fp7.driver.ost.config;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Valid;

@Configuration
public class KeycloakAdminClientConfig {

    @Value("${keycloak.proxy-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${driver.keycloak.admin-client-id}")
    private String adminClientId;

    @Value("${driver.keycloak.admin-client-secret}")
    private String adminClientSecret;


    @Bean
    public RealmResource realmResource() {
        Keycloak keycloak = KeycloakBuilder
                .builder()
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .serverUrl(serverUrl)
                .realm(realm)
                .clientId(adminClientId)
                .clientSecret(adminClientSecret)
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
                .build();
        return keycloak.realm(realm);
    }
}
