package pl.com.itti.app.driver.web;

import co.perpixel.dto.DTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.everit.json.schema.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.com.itti.app.driver.dto.AnswerDTO;
import pl.com.itti.app.driver.model.Answer;
import pl.com.itti.app.driver.service.AnswerService;
import pl.com.itti.app.driver.util.SchemaValidationException;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

//    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public AnswerDTO.Item answerQuestions(@RequestPart(value = "data") AnswerDTO.Form form,
//                                          @RequestPart(value = "attachments", required = false) MultipartFile[] files) throws IOException {
//        Answer answer;
//
//        try {
//            answer = answerService.createAnswer(form, files);
//        } catch (ValidationException ve) {
//            if (ve.getCausingExceptions().isEmpty()) {
//                throw new SchemaValidationException(ve);
//            } else {
//                throw new SchemaValidationException(ve.getCausingExceptions().get(0));
//            }
//        }
//
//        return DTO.from(answer, AnswerDTO.Item.class);
//    }

    // TODO endpoint used for testing backend
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public AnswerDTO.Item answerQuestions(@RequestParam("observationTypeId") Long observationTypeId,
                                          @RequestParam("trialSessionId") Long trialSessionId,
                                          @RequestParam("simulationTime") ZonedDateTime simulationTime,
                                          @RequestParam("fieldValue") String fieldValue,
                                          @RequestParam("formData") String formData,
                                          @RequestParam("trialRoleIds") List<Long> trialRoleIds,
                                          @RequestPart(value = "attachments", required = false) MultipartFile[] files) throws IOException {
        AnswerDTO.Form form = new AnswerDTO.Form();
        form.observationTypeId = observationTypeId;
        form.trialSessionId = trialSessionId;
        form.simulationTime = simulationTime;
        form.fieldValue = fieldValue;
        form.formData = new ObjectMapper().readTree(formData);
        form.trialRoleIds = trialRoleIds;

        Answer answer;

        try {
            answer = answerService.createAnswer(form, files);
        } catch (ValidationException ve) {
            if (ve.getCausingExceptions().isEmpty()) {
                throw new SchemaValidationException(ve);
            } else {
                throw new SchemaValidationException(ve.getCausingExceptions().get(0));
            }
        }

        return DTO.from(answer, AnswerDTO.Item.class);
    }
}
