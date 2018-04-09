package pl.com.itti.app.driver.web.rest;

import co.perpixel.dto.DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.com.itti.app.driver.service.ObservationTypeService;
import pl.com.itti.app.driver.web.dto.ObservationTypeDTO;

import java.util.List;

@RestController
@RequestMapping("/api/observation_types")
public class ObservationTypeController {

    @Autowired
    private ObservationTypeService observationTypeService;

    @GetMapping
    public List<ObservationTypeDTO.Item> findAll(@RequestParam("trialSessionId") Long trialSessionId) {
        return DTO.from(observationTypeService.find(trialSessionId), ObservationTypeDTO.Item.class);
    }
}
