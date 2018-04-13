package pl.com.itti.app.driver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.driver.dto.EventDTO;
import pl.com.itti.app.driver.model.Event;
import pl.com.itti.app.driver.repository.EventRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Page<EventDTO.ListItem> findByTrialSessionId(long trialSessionId, Pageable pageable) {
        Page<Event> eventList = eventRepository.findByTrialSessionId(trialSessionId, pageable);
        return eventList.map(EventDTO.ListItem::new);
    }
}
