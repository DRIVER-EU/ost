package pl.com.itti.app.driver.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.itti.app.driver.dto.TrialSessionDTO;
import pl.com.itti.app.driver.model.enums.SessionStatus;
import pl.com.itti.app.driver.service.TrialSessionService;

@RestController
@RequestMapping("/api/trialsession")
public class TrialSessionController {

    @Autowired
    private TrialSessionService trialSessionService;

    @GetMapping("/active")
    private Page<TrialSessionDTO.MinimalItem> findActive(Pageable pageable) {
        return trialSessionService.findByStatus(SessionStatus.STARTED, pageable);
    }
}
