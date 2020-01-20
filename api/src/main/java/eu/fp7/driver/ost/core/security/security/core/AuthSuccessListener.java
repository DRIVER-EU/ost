package eu.fp7.driver.ost.core.security.security.core;

import eu.fp7.driver.ost.core.security.security.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

@Component
public class AuthSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Override
    @Transactional
    public void onApplicationEvent(final AuthenticationSuccessEvent event) {
        // TODO this shouldn't be handled by the custom @Query, instead disable AuditorHandler (but, problems with parameters)
        authUserRepository.findOneByLogin(event.getAuthentication().getName())
                .ifPresent(authUser -> authUserRepository.updateLastLogin(authUser.getId(), Calendar.getInstance()));
    }
}
