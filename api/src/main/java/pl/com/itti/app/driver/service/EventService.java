package pl.com.itti.app.driver.service;

import co.perpixel.security.model.AuthUser;
import co.perpixel.security.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.driver.model.Event;
import pl.com.itti.app.driver.repository.EventRepository;

@Service
@Transactional(readOnly = true)
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private TrialUserService trialUserService;

    public Page<Event> findByTrialSessionId(long trialSessionId, Pageable pageable) {
        AuthUser authUser = authUserRepository.findOneCurrentlyAuthenticated()
                .orElseThrow(() -> new IllegalArgumentException("Session for current user is closed"));

        trialUserService.checkIsTrailSessionManager(authUser, trialSessionId);
        return eventRepository.findByTrialSessionId(trialSessionId, pageable);
    }
}
