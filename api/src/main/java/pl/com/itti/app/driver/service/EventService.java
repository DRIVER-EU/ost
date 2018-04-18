package pl.com.itti.app.driver.service;

import co.perpixel.security.model.AuthUser;
import co.perpixel.security.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.driver.dto.EventDTO;
import pl.com.itti.app.driver.model.Event;
import pl.com.itti.app.driver.model.TrialSession;
import pl.com.itti.app.driver.repository.EventRepository;
import pl.com.itti.app.driver.repository.TrialSessionRepository;

@Service
@Transactional
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private TrialSessionRepository trialSessionRepository;

    @Autowired
    private TrialUserService trialUserService;

    public Page<Event> findByTrialSessionId(long trialSessionId, Pageable pageable) {
        AuthUser authUser = authUserRepository.findOneCurrentlyAuthenticated()
                .orElseThrow(() -> new IllegalArgumentException("Session for current user is closed"));

        trialUserService.checkIsTrailSessionManager(authUser, trialSessionId);
        return eventRepository.findByTrialSessionId(trialSessionId, pageable);
    }

    public Event create(EventDTO.CreateFormItem formItem) {
        AuthUser authUser = authUserRepository.findOneCurrentlyAuthenticated()
                .orElseThrow(() -> new IllegalArgumentException("Session for current user is closed"));

        TrialSession trialSession = trialSessionRepository.findOne(formItem.trialSessionId);
        trialUserService.checkIsTrailSessionManager(authUser, formItem.trialSessionId);

        Event event = new Event();
        event.setTrialSession(trialSession);
        event.setIdEvent(formItem.idEvent);
        event.setName(formItem.name);
        event.setDescription(formItem.description);
        event.setLanguageVersion(formItem.languageVersion);
        event.setEventTime(formItem.eventTime);

        return eventRepository.save(event);
    }
}
