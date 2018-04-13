package pl.com.itti.app.driver.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.com.itti.app.driver.dto.EventDTO;
import pl.com.itti.app.driver.service.EventService;

@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/search")
    public Page<EventDTO.ListItem> findByTrialSessionId(@RequestParam(value = "trialsession_id") long trialSessionId,
                                                        Pageable pageable) {
        return eventService.findByTrialSessionId(trialSessionId, pageable);
    }
}