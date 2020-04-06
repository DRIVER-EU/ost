//package eu.fp7.driver.ost.core.security.security;
//
//import eu.fp7.driver.ost.driver.model.AuthUser;
//import eu.fp7.driver.ost.driver.repository.AuthUserRepository;
//import org.junit.Assert;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//
//import java.util.Optional;
//
//import static org.junit.Assert.*;
//
//public class KeycloakUtilTest {
//
//    @Autowired
//    AuthUserRepository authUserRepository;
//
//    @Autowired
//    KeycloakUtil keycloakUtil;
//
//    @Value("${driver.keycloak.is_enabled}")
//    private boolean keycloakIsEnabled;
//
//    @Value("${driver.keycloak.is_dominant}")
//    private boolean keycloakIsDominant;
//
//    @Test
//    public void authorizationTest() {
//        if (!keycloakIsEnabled) return;
//
//        String username = "user2";
//        String password = "user2";
////        if(authUserRepository.findOneByLogin(username).isPresent()) {
//        boolean  t = keycloakUtil.authorization(username, password);
//            Assert.assertTrue(keycloakUtil.authorization(username, password));
////        }
//    }
//}