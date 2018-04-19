package pl.com.itti.app.driver.web;

import co.perpixel.annotation.web.PostMapping;
import co.perpixel.dto.DTO;
import co.perpixel.dto.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.com.itti.app.driver.dto.EventDTO;
import pl.com.itti.app.driver.service.EventService;

@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/search")
    public PageDTO<EventDTO.ListItem> findByTrialSessionId(
            @RequestParam(value = "trialsession_id") long trialSessionId, Pageable pageable) {
        return DTO.from(eventService.findByTrialSessionId(trialSessionId, pageable), EventDTO.ListItem.class);
    }

    @PostMapping
    public EventDTO.FormItem create(@RequestBody @Validated EventDTO.FormItem formItem) {
        return DTO.from(eventService.create(formItem), EventDTO.FormItem.class);
    }
}
