package pl.com.itti.app.driver.service;

import co.perpixel.exception.EntityNotFoundException;
import co.perpixel.security.model.AuthUser;
import co.perpixel.security.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.driver.dto.EventDTO;
import pl.com.itti.app.driver.model.Event;
import pl.com.itti.app.driver.model.TrialRole;
import pl.com.itti.app.driver.model.TrialSession;
import pl.com.itti.app.driver.model.TrialUser;
import pl.com.itti.app.driver.repository.EventRepository;
import pl.com.itti.app.driver.repository.TrialRoleRepository;
import pl.com.itti.app.driver.repository.TrialSessionRepository;
import pl.com.itti.app.driver.repository.TrialUserRepository;

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
    private TrialUserRepository trialUserRepository;

    @Autowired
    private TrialRoleRepository trialRoleRepository;

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

        TrialSession trialSession = trialSessionRepository.findById(formItem.trialSessionId)
                .orElseThrow(() -> new EntityNotFoundException(TrialSession.class, formItem.trialSessionId));

        trialUserService.checkIsTrailSessionManager(authUser, formItem.trialSessionId);

        TrialUser trialUser = trialUserRepository.findOne(formItem.trialUserId);
        TrialRole trialRole = trialRoleRepository.findOne(formItem.trialRoleId);

        Event event = Event.builder()
                .trialSession(trialSession)
                .idEvent(formItem.idEvent)
                .name(formItem.name)
                .description(formItem.description)
                .languageVersion(formItem.languageVersion)
                .eventTime(formItem.eventTime)
                .trialUser(trialUser)
                .trialRole(trialRole)
                .build();

        return eventRepository.save(event);
    }
}
