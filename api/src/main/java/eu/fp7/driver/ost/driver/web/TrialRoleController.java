package eu.fp7.driver.ost.driver.web;

import eu.fp7.driver.ost.core.dto.Dto;
import eu.fp7.driver.ost.core.dto.PageDto;
import eu.fp7.driver.ost.driver.dto.TrialRoleDTO;
import eu.fp7.driver.ost.driver.service.TrialRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role")
public class TrialRoleController {

    @Autowired
    private TrialRoleService trialRoleService;

    @GetMapping
    private PageDto<TrialRoleDTO.FullItem> findByTrialId(
            @RequestParam(value = "trial_id") long trialId, Pageable pageable) {
        return Dto.from(trialRoleService.findByTrialId(trialId, pageable), TrialRoleDTO.FullItem.class);
    }
}
