package pl.com.itti.app.driver.web;

import co.perpixel.annotation.web.FindAllGetMapping;
import co.perpixel.annotation.web.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.itti.app.driver.dto.ObservationDTO;
import pl.com.itti.app.driver.model.Observation;
import pl.com.itti.app.driver.service.ObservationService;

import java.util.List;

@RestController
@RequestMapping("/api/anonymous/observation")
public class ObservationController {

    @Autowired
    private ObservationService observationService;
//
//    @FindAllGetMapping
//    public List<Observation> findAll() {
//        return observationService.findAll();
//    }
//
//    @PostMapping()
//    public ResponseEntity<Observation> postMessage(@RequestBody ObservationDTO formItem) {
//        return new ResponseEntity<>(observationService.addObservation(formItem), HttpStatus.CREATED);
//    }
}
