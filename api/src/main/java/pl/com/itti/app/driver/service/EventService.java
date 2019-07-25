package pl.com.itti.app.driver.service;

import co.perpixel.exception.EntityNotFoundException;
import co.perpixel.security.model.AuthUser;
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
    private TrialSessionRepository trialSessionRepository;

    @Autowired
    private TrialUserRepository trialUserRepository;

    @Autowired
    private TrialRoleRepository trialRoleRepository;

    @Autowired
    private TrialUserService trialUserService;

    public Page<Event> findByTrialSessionId(long trialSessionId, Pageable pageable) {
        AuthUser authUser = trialUserService.getCurrentUser();

        trialUserService.checkIsTrialSessionManager(authUser, trialSessionId);
        return eventRepository.findAllByTrialSessionId(trialSessionId, pageable);
    }

    public Event create(EventDTO.FormItem formItem) {
        AuthUser authUser = trialUserService.getCurrentUser();
        trialUserService.checkIsTrialSessionManager(authUser, formItem.trialSessionId);

        TrialSession trialSession = trialSessionRepository.findById(formItem.trialSessionId)
                .orElseThrow(() -> new EntityNotFoundException(TrialSession.class, formItem.trialSessionId));

        if (formItem.trialRoleId != null && formItem.trialUserId != null) {
            throw new InvalidDataException("Event can't be connected to TrialRole and TrialUser");
        }

        TrialUser trialUser = findUser(formItem.trialUserId);
        TrialRole trialRole = findRole(formItem.trialRoleId);

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

    private TrialUser findUser(Long id) {
        if (id != null) {
            return trialUserRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(TrialUser.class, id));
        }
        return null;
    }

    private TrialRole findRole(Long id) {
        if (id != null) {
            return trialRoleRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(TrialRole.class, id));
        }
        return null;
    }
}
