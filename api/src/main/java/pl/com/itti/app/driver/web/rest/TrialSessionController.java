package pl.com.itti.app.driver.web.rest;

import co.perpixel.dto.DTO;
import co.perpixel.dto.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.itti.app.driver.model.enums.SessionStatus;
import pl.com.itti.app.driver.service.TrialSessionService;
import pl.com.itti.app.driver.web.dto.TrialSessionDTO;

@RestController
@RequestMapping("/api/trialsession")
public class TrialSessionController {

    @Autowired
    private TrialSessionService trialSessionService;

    @GetMapping("/active")
    private PageDTO<TrialSessionDTO.ListItem> findActive(Pageable pageable) {
        return DTO.from(trialSessionService.findByStatus(SessionStatus.ACTIVE, pageable), TrialSessionDTO.ListItem.class);
    }
}
