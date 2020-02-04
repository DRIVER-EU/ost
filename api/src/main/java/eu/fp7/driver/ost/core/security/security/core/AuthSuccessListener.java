package eu.fp7.driver.ost.core.security.security.core;

import eu.fp7.driver.ost.core.security.security.model.AuthUser;
import eu.fp7.driver.ost.core.security.security.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import eu.fp7.driver.ost.core.security.security.KeycloakUtil;

import java.util.Calendar;
import java.util.Optional;

@Component
public class AuthSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    KeycloakUtil keycloakUtil;

    @Value("${driver.keycloak.is_dominant}")
    private boolean keycloakIsDominant;

    @Override
    @Transactional
    public void onApplicationEvent(final AuthenticationSuccessEvent event) {
        // TODO this shouldn't be handled by the custom @Query, instead disable AuditorHandler (but, problems with parameters)
        String scope = null;
        if (!event.getAuthentication().isAuthenticated()) return;
        Optional<AuthUser> authUser = authUserRepository.findOneByLogin(event.getAuthentication().getName());
        if (!authUser.isPresent()) return;
        String login = authUser.get().getLogin();
        String password = authUser.get().getPassword();
        if (keycloakIsDominant && !keycloakUtil.authorization(login, password)) {
            event.getAuthentication().setAuthenticated(false);
            return;
        }
        authUserRepository.updateLastLogin(authUser.get().getId(), Calendar.getInstance());
    }
}





