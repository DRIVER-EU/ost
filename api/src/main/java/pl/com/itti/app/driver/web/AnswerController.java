package pl.com.itti.app.driver.web;

import co.perpixel.annotation.web.FindAllGetMapping;
import co.perpixel.dto.DTO;
import co.perpixel.dto.PageDTO;
import org.everit.json.schema.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.com.itti.app.driver.dto.AnswerDTO;
import pl.com.itti.app.driver.model.Answer;
import pl.com.itti.app.driver.service.AnswerService;
import pl.com.itti.app.driver.util.InvalidDataException;
import pl.com.itti.app.driver.util.SchemaValidationException;

import java.io.IOException;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    private static final String AUDIO = "audio";

    private static final String IMAGE = "image";

    @Autowired
    private AnswerService answerService;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public AnswerDTO.Item answerQuestions(@RequestPart(value = "data") AnswerDTO.Form form,
                                          @RequestPart(value = "attachments", required = false) MultipartFile[] files) throws IOException {
        assertThatFilesAreValid(files);
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

    @FindAllGetMapping
    public PageDTO<AnswerDTO.Item> findAll(@RequestParam("trialsession_id") long trialSessionId) {
        return DTO.from(answerService.findAll(trialSessionId), AnswerDTO.Item.class);
    }

    private void assertThatFilesAreValid(MultipartFile[] files) {
        if (files.length > 0) {
            for (MultipartFile file : files) {
                if (!file.getContentType().startsWith(AUDIO) && !file.getContentType().startsWith(IMAGE)) {
                    throw new InvalidDataException("Invalid type of attachment: " + file.getContentType());
                }
            }
        }
    }

    // TODO endpoint used for testing backend
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
