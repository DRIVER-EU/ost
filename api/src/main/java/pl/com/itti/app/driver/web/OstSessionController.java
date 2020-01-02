package pl.com.itti.app.driver.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.itti.app.driver.service.OstSessionService;
import pl.com.itti.app.driver.service.TrialSessionService;
import pl.com.itti.app.driver.util.TrailDeleteException;


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


