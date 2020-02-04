package eu.fp7.driver.ost.core.security.security;

//import eu.fp7.driver.ost.core.security.KeycloakClient;
import eu.fp7.driver.ost.driver.util.LogService;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.keycloak.Config;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.authorization.client.resource.AuthorizationResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.authorization.AuthorizationRequest;
import org.keycloak.representations.idm.authorization.AuthorizationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class KeycloakUtil {

    @Value("${driver.keycloak.is_enabled}")
    private boolean keycloakIsEnabled;

    enum ParametersName {client, }

    @PostConstruct
    void init() {
        String keycloakServer;
        String realm;
        String clientId;
        String secret;
        String username ;
        String password ;


//
//        Map <String, Map<String,String>> realmMap = new HashMap<>();
//
//        realmMap.put("master", null);
//        Map<String, String> loginParameters = new HashMap<>();
//        loginParameters.put("realm","master");
//
//        loginParameters.put("keycloakServer", "http://localhost:8070/auth");
//        loginParameters.put("realm", "driver2");
//        loginParameters.put("clientId",  "ost_app");
//        loginParameters.put("secret",  "b5762880-323a-4c92-ab6a-1413d1c5af93");
//        createAuthzClient()
//        loginParameters.put("username",  "user1");
//        loginParameters.put("password",  "user1");
//
//
//        realmMap.put("driver2", null);



        keycloakServer = "http://localhost:8070/auth";

        realm = "master";
        clientId = "ost";
        secret = "f4c78eb8-9bd1-4e76-98d2-e60e13f32c51";

//        username = "admin";
//        password = "admin";
//        authorization(createAuthzClient(keycloakServer,realm,clientId,secret),username,password);
//
//        username = "ost_admin";
//        password = "ost_admin";
//        authorization(createAuthzClient(keycloakServer,realm,clientId,secret),username,password);
//
//        username = "ost_user1";
//        password = "ost_user1";
//        authorization(createAuthzClient(keycloakServer,realm,clientId,secret),username,password);


        realm = "driver2";
        clientId = "ost_app";
        secret = "b5762880-323a-4c92-ab6a-1413d1c5af93";
        AuthzClient authzClient = createAuthzClient(keycloakServer,realm,clientId,secret);


        username = "user1";
        password = "user1";
        authorization(authzClient,username,password);

        username = "user2";
        password = "user2";
        authorization(authzClient,username,password);

        username = "user3";
        password = "user31";
        authorization(authzClient,username,password);


    }


    public AuthzClient createAuthzClient(String keycloakServer, String realm, String clientId, String secret) {
        if (!keycloakIsEnabled) return null;
        AuthzClient authzClient = null;
        Configuration configuration = new Configuration();
        configuration.setAuthServerUrl(keycloakServer);
        configuration.setRealm(realm);
        configuration.setResource(clientId);
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("secret", secret);
        configuration.setCredentials(credentials);
        configuration.setSslRequired("false");

//        configuration.setPublicClient(true);

//        configuration.setUseResourceRoleMappings(false);
//        configuration.set

        return AuthzClient.create(configuration);
    }


    public String authorization(AuthzClient authzClient, String userName, String userPassword){
        if(!keycloakIsEnabled ||authzClient==null) return null;
        String rpt = "?";
        String scope = "?";
        AuthorizationRequest request = new AuthorizationRequest();
        try {
            AccessTokenResponse accessTokenResponse = authzClient.obtainAccessToken(userName, userPassword);

            AuthorizationResource authorization = authzClient.authorization(userName, userPassword);
            AuthorizationResponse response = authorization.authorize(request);
            rpt = response.getToken();
            scope = response.getScope();
            System.out.println("######## KEYCLOAK: "+userName+"/"+userPassword + "/"+scope);
        }catch (Exception e){
            System.out.println("KEYCLOAK login failed: "+userName+"/"+userPassword + "/"+scope);
//            LogService.saveExceptionToLogFile(e);
        }
        return scope;
    }

//    public void run() throws Exception {
//
//        // initiate the Keycloak connection
//
//
//        KeycloakClient kc = new KeycloakClient(keycloakServer, realm, clientId, username, password);
//
//        // retrieve tokens
//        KeycloakClient.TokenCollection tokens = kc.getTokens();
//
//        // Once we have the tokens, we can use the access token to call the protected API throught APIMAN
//        try(CloseableHttpClient httpclient = HttpClients.createDefault()) {
//
//            String apiEndpoint = "http://localhost:8080/my_awesome_api";
//            HttpGet get = new HttpGet(apiEndpoint);
//            get.setHeader("Authorization", "Bearer " + tokens.getAccessToken());
//
//            String result = httpclient.execute(get, response -> {
//
//                int status = response.getStatusLine().getStatusCode();
//                if (status >= 200 && status < 300) {
//                    return EntityUtils.toString(response.getEntity());
//
//                } else {
//                    throw new ClientProtocolException("Unexpected response status: " + status);
//                }
//            });
//
//            System.out.println("API response : " + result);
//        }
//
//
//        // eventually you'll have to refresh the tokens.
//        KeycloakClient.TokenCollection newTokens = kc.refreshTokens(tokens.getRefreshToken());
//
//        System.out.println("old access token : " + tokens.getAccessToken());
//        System.out.println("new access token : " + newTokens.getAccessToken());
//
//    }

//    public AuthzClient authzClient(KeycloakSpringBootProperties kcProperties) {
//        //org.keycloak.authorization.client.Configuration
//        Configuration configuration =
//                new Configuration(kcProperties.getAuthServerUrl(),
//                        kcProperties.getRealm(),
//                        kcProperties.getResource(),
//                        kcProperties.getCredentials(), null);
//
//        return AuthzClient.create(configuration);
//    }
//
//    public AccessTokenResponse token() {
//        return authzClient.obtainAccessToken();
}