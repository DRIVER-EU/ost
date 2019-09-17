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
import pl.com.itti.app.driver.form.NewSessionForm;
import pl.com.itti.app.driver.model.enums.SessionStatus;
import pl.com.itti.app.driver.service.TrialSessionService;
import pl.com.itti.app.driver.util.UserFileProperties;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trialsessions")
public class TrialSessionController {

    @Autowired
    private TrialSessionService trialSessionService;

    @Autowired
    private UserFileProperties userFileProperties;

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

    @PutMapping("/{trialsession_id:\\d+}/changeStatus")
    public void changeStatus(@PathVariable(value = "trialsession_id") long trialSessionId, @RequestParam(value = "status") String status) {
        trialSessionService.changeStatus(trialSessionId, status);
    }

    @RequestMapping("/manual/{trialsession_id}/{is_manual}")
    public void changeManualStageChange(@PathVariable long trialsession_id, @PathVariable boolean is_manual) {
        trialSessionService.setMaualStageChange(trialsession_id, is_manual);
    }

    @PutMapping
    private TrialSessionDTO.FullItem updateLastTrialStage(@PathVariable(value = "id") Long trialSessionId,
                                                          @RequestBody @Validated TrialStageDTO.MinimalItem minimalItem) {
        return DTO.from(trialSessionService.updateLastTrialStage(trialSessionId, minimalItem.id), TrialSessionDTO.FullItem.class);
    }

    @PostMapping("createNewSessionEmail")
    public void createNewSesson(@RequestBody NewSessionForm newSessionForm) {
        trialSessionService.createNewSession(newSessionForm, true);
    }

    @PostMapping("createNewSessionFile")
    @ResponseBody
    public void createNewSessonTxt(HttpServletResponse response, @RequestBody NewSessionForm newSessionForm) throws IOException {
        java.nio.file.Files.createDirectories(Paths.get(userFileProperties.getTxtDir()));
        List<String> lines = trialSessionService.createNewSession(newSessionForm, false);

        Files.write(Paths.get(userFileProperties.getTxtDir() + "newSession.txt"), lines, Charset.defaultCharset());
        File file = new File(userFileProperties.getTxtDir() + "newSession.txt");

        byte[] data = Files.readAllBytes(file.toPath());

        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment; filename = newSession.txt");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.getOutputStream().write(data);

        file.delete();
    }

    @GetMapping("/trials")
    public Map<Long, String> getTrials() {
        return trialSessionService.getTrials();
    }

    @PostMapping("newSessionValues")
    public JsonNode newSessionValues(@RequestParam(value = "trial_id") long trialId) {
        return trialSessionService.newSessionValues(trialId);
    }
}
