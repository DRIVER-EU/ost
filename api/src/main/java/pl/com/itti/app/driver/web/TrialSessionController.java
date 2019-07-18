package pl.com.itti.app.driver.web;

import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.com.itti.app.core.annotation.FindAllGetMapping;
import pl.com.itti.app.core.dto.Dto;
import pl.com.itti.app.core.dto.PageDto;
import pl.com.itti.app.driver.dto.TrialSessionDTO;
import pl.com.itti.app.driver.dto.TrialStageDTO;
import pl.com.itti.app.driver.form.NewSessionForm;
import pl.com.itti.app.driver.model.TrialSession;
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
        return Dto.from(trialSessionService.findOneByManager(answerId), TrialSessionDTO.ListItem.class);
    }

    @FindAllGetMapping
    public PageDto<TrialSessionDTO.ListItem> findAllForTrialSessionManager(Pageable pageable) {
        return Dto.from(trialSessionService.findAllByManager(pageable), TrialSessionDTO.ListItem.class);
    }

    @GetMapping("/active")
    private PageDto<TrialSessionDTO.ActiveListItem> findActive(Pageable pageable) {
        return trialSessionService.findByStatus(SessionStatus.ACTIVE, pageable);
    }

    @PutMapping("/{trialsession_id:\\d+}/changeStatus")
    public void changeStatus(@PathVariable(value = "trialsession_id") long trialSessionId, @RequestParam(value = "status") String status) {
        trialSessionService.changeStatus(trialSessionId, status);
    }

    @RequestMapping("/manual/{trialsession_id}/{is_manual}")
    public String changeManualStageChange(@PathVariable long trialsession_id, @PathVariable boolean is_manual) {
        return trialSessionService.setManualStageChange(trialsession_id, is_manual);
    }

    @PutMapping
    private TrialSessionDTO.FullItem updateLastTrialStage(@PathVariable(value = "id") Long trialSessionId,
                                                          @RequestBody @Validated TrialStageDTO.MinimalItem minimalItem) {
        return Dto.from(trialSessionService.updateLastTrialStage(trialSessionId, minimalItem.id), TrialSessionDTO.FullItem.class);
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

    @GetMapping("/admin/getFullTrialSession")
    public ResponseEntity getTrialSession(@RequestParam(value = "id") long id) {
        try{
            TrialSessionDTO.AdminEditItem adminTrialSessionDTO = new TrialSessionDTO.AdminEditItem();
            TrialSession trialSession = trialSessionService.findById(id);
            adminTrialSessionDTO.toDto(trialSession);
            ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body(adminTrialSessionDTO);
            return responseEntity;
        }
        catch (Exception e)
        {
            ResponseEntity responseEntity = getResponseEntity(e);
            return responseEntity;
        }

    }

    @PostMapping("/admin/addNewTrialSession" )
    public ResponseEntity addNewTrialSession(@RequestBody TrialSessionDTO.AdminEditItem adminTrialSessionDTO) {

        try{
            TrialSession trialSeesion = trialSessionService.insert(adminTrialSessionDTO);
            adminTrialSessionDTO.toDto(trialSeesion);

        }
        catch (Exception e)
        {
            ResponseEntity responseEntity = getResponseEntity(e);
            return responseEntity;
        }
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body(adminTrialSessionDTO);
        return responseEntity;
    }



    @DeleteMapping("/admin/deleteTrialSession")
    public ResponseEntity deleteTrial(@RequestParam(value = "id") long id) {
        try {
            trialSessionService.delete(id);
            ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body("Trial Session id =" + id + " is deleted");
            return responseEntity;
        }
        catch (Exception e)
        {
            ResponseEntity responseEntity = getResponseEntity(e);
            return responseEntity;
        }
    }

    @PutMapping("/admin/updateTrialSession")
    public ResponseEntity updateTrial(@RequestBody TrialSessionDTO.AdminEditItem  adminTrialSessionDTO) {
        try {
            TrialSession trialSession = trialSessionService.update(adminTrialSessionDTO);
            adminTrialSessionDTO.toDto(trialSession);
        }
        catch (Exception e)
        {
            ResponseEntity responseEntity = getResponseEntity(e);
            return responseEntity;
        }
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body(adminTrialSessionDTO);
        return responseEntity;
    }

    private ResponseEntity getResponseEntity(Exception e) {
        JSONObject jsonAnswer = new JSONObject();
        JSONArray serverErrorList = new JSONArray();
        JSONObject serverError = new JSONObject();
        serverError.put("serviceError", "Errors in service." + e.getMessage());
        serverErrorList.put(serverError);
        jsonAnswer.put("status", HttpStatus.BAD_REQUEST);
        jsonAnswer.put("errors", serverErrorList);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonAnswer.toString());
    }
}
