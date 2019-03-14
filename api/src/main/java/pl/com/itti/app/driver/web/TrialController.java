package pl.com.itti.app.driver.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static pl.com.itti.app.driver.util.TrialStage.getOstTrialId;
import static pl.com.itti.app.driver.util.TrialStage.getOstTrialSessionId;
import static pl.com.itti.app.driver.util.TrialStage.getOstTrialStageId;

@RestController
@RequestMapping("/api")
public class TrialController {

    @GetMapping("/ostTrialId")
    public Integer ostTrialId() {
        return getOstTrialId();
    }

    @GetMapping("/ostTrialSessionId")
    public Integer timeElapsed() {
        return getOstTrialSessionId();
    }

    @GetMapping("/ostTrialStageId")
    public Integer ostTrialStageId(){
        return getOstTrialStageId();
    }

}
