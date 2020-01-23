package eu.fp7.driver.ost.driver.web;

import eu.fp7.driver.ost.driver.service.OstSessionService;
import eu.fp7.driver.ost.driver.service.TrialSessionService;
import eu.fp7.driver.ost.driver.util.TrailDeleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class OstSessionController {

  @Autowired
  TrialSessionService trialSessionService;

  @Autowired
  OstSessionService ostSessionService;

  @ResponseBody
  @GetMapping("/delete/session")
  public ResponseEntity deleteOstSessionById(@RequestParam Long ostSessionId) throws TrailDeleteException {
    validateRequest(ostSessionId);
    ostSessionService.deleteTrialSession(ostSessionId);
    return ResponseEntity.status(HttpStatus.OK).body("trialSession with id=" + ostSessionId + " successfully deleted");
  }

  private void validateRequest(@RequestParam Long ostSessionId) {
    if (ostSessionId == null) {
      new TrailDeleteException("Null parameter");
    }
  }
}


