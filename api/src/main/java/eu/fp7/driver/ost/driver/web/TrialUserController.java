package eu.fp7.driver.ost.driver.web;

import eu.fp7.driver.ost.Application;
import eu.fp7.driver.ost.core.dto.Dto;
import eu.fp7.driver.ost.core.dto.PageDto;
import eu.fp7.driver.ost.driver.dto.TrialUserDTO;
import eu.fp7.driver.ost.driver.service.TrialUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
