//package eu.fp7.driver.ost.core.security.security;
//
//import eu.fp7.driver.ost.driver.util.LogService;
//import org.keycloak.authorization.client.AuthzClient;
//import org.keycloak.authorization.client.Configuration;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import javax.annotation.PostConstruct;
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class KeycloakUtil {
//
//    @Value("${driver.keycloak.is_enabled}")
//    private boolean keycloakIsEnabled;
//    @Value("${driver.keycloak.is_dominant}")
//    private boolean keycloakIsDominant;
//    @Value("${driver.keycloak.server}")
//    private String keycloakServer;
//    @Value("${driver.keycloak.realm}")
//    private String realm;
//    @Value("${driver.keycloak.client}")
//    private String client;
//    @Value("${driver.keycloak.client.secret}")
//    private String clientSecret;
//
//    private AuthzClient authzClient;
//
//
//    @PostConstruct
//    void init() {
//        authzClient = createAuthzClient(keycloakServer,realm,client,clientSecret);
//    }
//
//
//    private AuthzClient createAuthzClient(String keycloakServer, String realm, String clientId, String secret) {
//        if (!keycloakIsEnabled) return null;
//        Configuration configuration = new Configuration();
//        configuration.setAuthServerUrl(keycloakServer);
//        configuration.setRealm(realm);
//        configuration.setResource(clientId);
//        Map<String, Object> credentials = new HashMap<>();
//        credentials.put("secret", secret);
//        configuration.setCredentials(credentials);
//        configuration.setSslRequired("false");
//
//        return AuthzClient.create(configuration);
//    }
//
//
//    public boolean authorization(String userName, String userPassword){
//        if(authzClient==null) return false;
//        try {
//            if(authzClient.obtainAccessToken(userName, userPassword)!=null){
//                return true;
//            }
//        }catch (Exception e){
//            LogService.saveExceptionToLogFile(e);
//        }
//        return false;
//    }
//}