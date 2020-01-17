package pl.com.itti.app.driver.util;

import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.representations.idm.authorization.AuthorizationRequest;
import org.keycloak.representations.idm.authorization.AuthorizationResponse;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class KeycloakUtil {

    AuthzClient authzClient;

    @PostConstruct
    void init(){
        authzClient = AuthzClient.create();
    }

    public void authorization(String login, String password){
        AuthorizationRequest request = new AuthorizationRequest();
        AuthorizationResponse response = authzClient.authorization(login, password).authorize(request);
        String rpt = response.getToken();
//        response.g

        System.out.println("You got an RPT: " + rpt);
    }
}