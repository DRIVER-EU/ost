package pl.com.itti.app.driver.web;

import co.perpixel.annotation.web.PostMapping;
import co.perpixel.dto.DTO;
import co.perpixel.dto.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.com.itti.app.driver.dto.AnswerDTO;
import pl.com.itti.app.driver.dto.ObservationTypeDTO;
import pl.com.itti.app.driver.model.Answer;
import pl.com.itti.app.driver.service.ObservationTypeService;

@RestController
@RequestMapping("/api/observationtypes")
public class ObservationTypeController {

    @Autowired
    private ObservationTypeService observationTypeService;

    @GetMapping
    public PageDTO<ObservationTypeDTO.ListItem> findAll(@RequestParam("trialsession_id") Long trialSessionId, Pageable pageable) {
        return DTO.from(observationTypeService.find(trialSessionId, pageable), ObservationTypeDTO.ListItem.class);
    }

    @GetMapping("/form")
    public ObservationTypeDTO.SchemaItem getSchemaForm(@RequestParam("observationtype_id") Long observationTypeId,
                                                       @RequestParam("trialsession_id") Long trialSessionId) {
        return observationTypeService.generateSchema(observationTypeId, trialSessionId);
    }

    @PostMapping("/{id:\\d+}/answers")
    public Answer createAnswerToObservationType(@PathVariable(value = "id") long observationTypeId,
                                                @RequestBody AnswerDTO.Form form) {
        return observationTypeService.createAnswer(observationTypeId, form);
    }
}
