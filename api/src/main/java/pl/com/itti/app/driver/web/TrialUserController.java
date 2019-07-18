package pl.com.itti.app.driver.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.com.itti.app.Application;
import pl.com.itti.app.driver.dto.TrialUserDTO;
import pl.com.itti.app.driver.service.TrialUserService;
import pl.com.itti.app.core.dto.Dto;
import pl.com.itti.app.core.dto.PageDto;

@RestController
@RequestMapping("/api/user")
public class TrialUserController {

    @Autowired
    private TrialUserService trialUserService;

    @GetMapping
    private PageDto<TrialUserDTO.ListItem> findByTrialSessionId(
            @RequestParam(value = "trialsession_id") long trialSessionId, Pageable pageable) {
        return Dto.from(trialUserService.findByTrialSessionId(trialSessionId, pageable), TrialUserDTO.ListItem.class);
    }

    @GetMapping("/version")
    public String getVersion() {
        return Application.version;
    }
}
