package pl.com.itti.app.driver.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.com.itti.app.driver.dto.TrialStageDTO;
import pl.com.itti.app.driver.service.TrialStageService;
import pl.com.itti.app.core.annotation.FindAllGetMapping;
import pl.com.itti.app.core.dto.Dto;
import pl.com.itti.app.core.dto.PageDto;

@RestController
@RequestMapping("api/stages")
public class TrialStageController {

    @Autowired
    private TrialStageService trialStageService;

    @FindAllGetMapping
    private PageDto<TrialStageDTO.ListItem> findByTrialId(
            @RequestParam(value = "trial_id") long trialId, Pageable pageable) {
        return Dto.from(trialStageService.findByTrialId(trialId, pageable), TrialStageDTO.ListItem.class);
    }
}
