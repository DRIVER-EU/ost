package pl.com.itti.app.driver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.driver.dto.EventDTO;
import pl.com.itti.app.driver.model.Event;
import pl.com.itti.app.driver.repository.EventRepository;
import pl.com.itti.app.driver.repository.UserRoleEventRepository;

@Service
@Transactional(readOnly = true)
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRoleEventRepository userRoleEventRepository;

    public Page<EventDTO.ListItem> findByTrialSessionId(long trialSessionId, Pageable pageable) {
        Page<Event> eventList = eventRepository.findByTrialSessionId(trialSessionId, pageable);
        return eventList.map(EventDTO.ListItem::new);
    }
}
