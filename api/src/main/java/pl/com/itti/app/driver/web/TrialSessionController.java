package pl.com.itti.app.driver.web;

import co.perpixel.annotation.web.FindAllGetMapping;
import co.perpixel.annotation.web.PutMapping;
import co.perpixel.dto.DTO;
import co.perpixel.dto.PageDTO;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.com.itti.app.driver.dto.TrialSessionDTO;
import pl.com.itti.app.driver.dto.TrialStageDTO;
import pl.com.itti.app.driver.model.enums.SessionStatus;
import pl.com.itti.app.driver.service.TrialSessionService;

import java.util.List;

@RestController
@RequestMapping("/api/trialsessions")
public class TrialSessionController {

    @Autowired
    private TrialSessionService trialSessionService;

    @GetMapping("/{trialsession_id:\\d+}")
    public TrialSessionDTO.ListItem findOneForTrialSessionManager(@PathVariable(value = "trialsession_id") long answerId) {
        return DTO.from(trialSessionService.findOneByManager(answerId), TrialSessionDTO.ListItem.class);
    }

    @FindAllGetMapping
    public PageDTO<TrialSessionDTO.ListItem> findAllForTrialSessionManager(Pageable pageable) {
        return DTO.from(trialSessionService.findAllByManager(pageable), TrialSessionDTO.ListItem.class);
    }

    @GetMapping("/active")
    private PageDTO<TrialSessionDTO.ActiveListItem> findActive(Pageable pageable) {
        return trialSessionService.findByStatus(SessionStatus.ACTIVE, pageable);
    }

    @PostMapping("/changeStatus")
    public void changeStatus(@RequestParam(value = "trialsession_id") long answerId, @RequestParam(value = "status") String status) {
        trialSessionService.changeStatus(answerId, status);
    }

    @PutMapping
    private TrialSessionDTO.FullItem updateLastTrialStage(@PathVariable(value = "id") Long trialSessionId,
                                                          @RequestBody @Validated TrialStageDTO.MinimalItem minimalItem) {
        return DTO.from(trialSessionService.updateLastTrialStage(trialSessionId, minimalItem.id), TrialSessionDTO.FullItem.class);
    }

    @GetMapping("/trials")
    public List<String> getTrials() {
        return trialSessionService.getTrials();
    }

    @PostMapping("newSessionValues")
    public JsonNode newSessionValues(@RequestParam(value = "trial_id") long trialId) {
        return trialSessionService.newSessionValues(trialId);
    }

}
