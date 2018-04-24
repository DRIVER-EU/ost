package pl.com.itti.app.driver.web;

import co.perpixel.annotation.web.FindAllGetMapping;
import co.perpixel.dto.DTO;
import co.perpixel.dto.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.com.itti.app.driver.dto.TrialStageDTO;
import pl.com.itti.app.driver.service.TrialStageService;

@RestController
@RequestMapping("api/stages")
public class TrialStageController {

    @Autowired
    private TrialStageService trialStageService;

    @FindAllGetMapping
    private PageDTO<TrialStageDTO.ListItem> findByTrialSessionId(
            @RequestParam(value = "trialsession_id") long trialSessionId, Pageable pageable) {
        return DTO.from(trialStageService.findByTrialSessionId(trialSessionId, pageable), TrialStageDTO.ListItem.class);
    }
}
