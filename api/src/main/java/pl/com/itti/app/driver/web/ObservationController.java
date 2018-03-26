package pl.com.itti.app.driver.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.itti.app.driver.service.ObservationService;

@RestController
@RequestMapping("/api/anonymous/answer")
public class ObservationController {

    @Autowired
    private ObservationService observationService;
//
//    @FindAllGetMapping
//    public List<Answer> findAll() {
//        return observationService.findAll();
//    }
//
//    @PostMapping()
//    public ResponseEntity<Answer> postMessage(@RequestBody ObservationDTO formItem) {
//        return new ResponseEntity<>(observationService.addObservation(formItem), HttpStatus.CREATED);
//    }
}
