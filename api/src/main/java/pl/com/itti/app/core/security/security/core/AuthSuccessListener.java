package pl.com.itti.app.core.security.security.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.core.security.security.repository.AuthUserRepository;

import java.time.ZonedDateTime;

@Component
public class AuthSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Override
    @Transactional
    public void onApplicationEvent(final AuthenticationSuccessEvent event) {
        // TODO this shouldn't be handled by the custom @Query, instead disable AuditorHandler (but, problems with parameters)
        authUserRepository.findOneByLogin(event.getAuthentication().getName())
                .ifPresent(authUser -> authUserRepository.updateLastLogin(authUser.getId(), ZonedDateTime.now()));
    }
}
