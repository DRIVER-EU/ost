package pl.com.itti.app.driver.web;

import co.perpixel.annotation.web.FindAllGetMapping;
import co.perpixel.dto.DTO;
import co.perpixel.dto.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.com.itti.app.driver.dto.ObservationTypeDTO;
import pl.com.itti.app.driver.service.ObservationTypeService;

@RestController
@RequestMapping("/api/observationtypes")
public class ObservationTypeController {

    @Autowired
    private ObservationTypeService observationTypeService;

    @FindAllGetMapping
    public PageDTO<ObservationTypeDTO.ListItem> findAll(@RequestParam("trialsession_id") Long trialSessionId, Pageable pageable) {
        return DTO.from(observationTypeService.find(trialSessionId, pageable), ObservationTypeDTO.ListItem.class);
    }

    @GetMapping("/form")
    public ObservationTypeDTO.SchemaItem getSchemaForm(@RequestParam("observationtype_id") Long observationTypeId,
                                                       @RequestParam("trialsession_id") Long trialSessionId) {
        return observationTypeService.generateSchema(observationTypeId, trialSessionId);
    }
}
