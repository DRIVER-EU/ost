package pl.com.itti.app.driver.web.rest;

import co.perpixel.dto.DTO;
import co.perpixel.dto.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.com.itti.app.driver.service.ObservationTypeService;
import pl.com.itti.app.driver.web.dto.ObservationTypeDTO;

@RestController
@RequestMapping("/api/observation_types")
public class ObservationTypeController {

    @Autowired
    private ObservationTypeService observationTypeService;

    @GetMapping
    public PageDTO<ObservationTypeDTO.ListItem> findAll(@RequestParam("trialSessionId") Long trialSessionId, Pageable pageable) {
        return DTO.from(observationTypeService.find(trialSessionId, pageable), ObservationTypeDTO.ListItem.class);
    }

    @GetMapping("/form")
    public ObservationTypeDTO.SchemaItem getSchemaForm(@RequestParam("observationTypeId") Long observationTypeId) {
        return observationTypeService.generateSchema(observationTypeId);
    }

}
