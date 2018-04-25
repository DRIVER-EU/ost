package pl.com.itti.app.driver.web;

import co.perpixel.dto.DTO;
import org.everit.json.schema.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.com.itti.app.driver.dto.AnswerDTO;
import pl.com.itti.app.driver.model.Answer;
import pl.com.itti.app.driver.service.AnswerService;
import pl.com.itti.app.driver.util.SchemaValidationException;

import java.io.IOException;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public AnswerDTO.Item answerQuestions(@RequestPart(value = "data") AnswerDTO.Form form,
                                          @RequestPart(value = "attachments", required = false) MultipartFile[] files) throws IOException {
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

//    // TODO endpoint used for testing backend
//    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public AnswerDTO.Item answerQuestions(@RequestParam("observationTypeId") Long observationTypeId,
//                                          @RequestParam("trialSessionId") Long trialSessionId,
//                                          @RequestParam("simulationTime") String simulationTime,
//                                          @RequestParam("fieldValue") String fieldValue,
//                                          @RequestParam("formData") String formData,
//                                          @RequestParam("trialRoleIds") List<Long> trialRoleIds,
//                                          @RequestParam(value = "texts", required = false) List<String> texts,
//                                          @RequestParam(value = "longitude", required = false) List<Float> longitude,
//                                          @RequestParam(value = "latitude", required = false) List<Float> latitude,
//                                          @RequestParam(value = "altitude", required = false) List<Float> altitude,
//                                          @RequestParam(value = "attachments", required = false) MultipartFile[] files) throws IOException {
//        AnswerDTO.Form form = new AnswerDTO.Form();
//        form.observationTypeId = observationTypeId;
//        form.trialSessionId = trialSessionId;
//        form.simulationTime = ZonedDateTime.parse(simulationTime);
//        form.fieldValue = fieldValue;
//        form.formData = new ObjectMapper().readTree(formData);
//        form.trialRoleIds = trialRoleIds;
//        form.descriptions = texts;
//        List<AttachmentDTO.Coordinates> coordinates = new ArrayList<>();
//        for (int i = 0; i < longitude.size(); i++) {
//            AttachmentDTO.Coordinates coord = new AttachmentDTO.Coordinates();
//            coord.longitude = longitude.get(i);
//            coord.latitude = latitude.get(i);
//            coord.altitude = altitude.get(i);
//            coordinates.add(coord);
//        }
//        form.coordinates = coordinates;
//
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
}
