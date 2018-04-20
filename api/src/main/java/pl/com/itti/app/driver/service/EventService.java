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
import pl.com.itti.app.driver.util.InvalidDataException;

import java.time.LocalDateTime;

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

        trialUserService.checkIsTrialSessionManager(authUser, trialSessionId);
        return eventRepository.findAllByTrialSessionId(trialSessionId, pageable);
    }

    public Event create(EventDTO.FormItem formItem) {
        AuthUser authUser = authUserRepository.findOneCurrentlyAuthenticated()
                .orElseThrow(() -> new IllegalArgumentException("Session for current user is closed"));

        TrialSession trialSession = trialSessionRepository.findById(formItem.trialSessionId)
                .orElseThrow(() -> new EntityNotFoundException(TrialSession.class, formItem.trialSessionId));

        trialUserService.checkIsTrialSessionManager(authUser, formItem.trialSessionId);

        if (formItem.trialRoleId > 0 && formItem.trialUserId > 0) {
            throw new InvalidDataException("Event can't be connected to TrialRole and TrialUser");
        }

        TrialUser trialUser = trialUserRepository.findOne(formItem.trialUserId);
        TrialRole trialRole = trialRoleRepository.findOne(formItem.trialRoleId);

        Event event = Event.builder()
                .trialSession(trialSession)
                .name(formItem.name)
                .description(formItem.description)
                .languageVersion(formItem.languageVersion)
                .eventTime(LocalDateTime.now())
                .trialUser(trialUser)
                .trialRole(trialRole)
                .build();

        return eventRepository.save(event);
    }
}
