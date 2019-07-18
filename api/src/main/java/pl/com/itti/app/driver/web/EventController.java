package pl.com.itti.app.driver.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.com.itti.app.core.dto.Dto;
import pl.com.itti.app.core.dto.PageDto;
import pl.com.itti.app.driver.dto.EventDTO;
import pl.com.itti.app.driver.service.EventService;

@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/search")
    public PageDto<EventDTO.ListItem> findByTrialSessionId(
            @RequestParam(value = "trialsession_id") long trialSessionId, Pageable pageable) {
        return Dto.from(eventService.findByTrialSessionId(trialSessionId, pageable), EventDTO.ListItem.class);
    }

    @PostMapping
    public EventDTO.Item create(@RequestBody @Validated EventDTO.FormItem formItem) {
        return Dto.from(eventService.create(formItem), EventDTO.Item.class);
    }
}
