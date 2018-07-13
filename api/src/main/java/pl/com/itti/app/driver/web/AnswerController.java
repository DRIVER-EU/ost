package pl.com.itti.app.driver.web;

import co.perpixel.annotation.web.FindAllGetMapping;
import co.perpixel.dto.DTO;
import org.everit.json.schema.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.com.itti.app.driver.dto.AnswerDTO;
import pl.com.itti.app.driver.model.Answer;
import pl.com.itti.app.driver.service.AnswerService;
import pl.com.itti.app.driver.util.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private CSVProperties csvProperties;

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
    public List<AnswerDTO.ListItem> findAll(@RequestParam("trialsession_id") long trialSessionId,
                                            @RequestParam("search") String text) {
        return DTO.from(answerService.findAll(trialSessionId, text), AnswerDTO.ListItem.class);
    }

    @GetMapping("/csv-file")
    public void getCSVFile(HttpServletResponse response, @RequestParam(value = "trialsession_id") long trialSessionId) throws IOException {
        if (!java.nio.file.Files.exists(Paths.get(csvProperties.getCsvDir()))) {
            java.nio.file.Files.createDirectories(Paths.get(csvProperties.getCsvDir()));
        }

        String fileName = UUID.randomUUID().toString() + ".csv";
        File file = new File(Paths.get(csvProperties.getCsvDir()) + "/" + fileName);
        FileWriter writer = new FileWriter(file.getAbsolutePath());
        answerService.createCSVFile(writer, trialSessionId);

        writer.flush();
        writer.close();

        byte[] data = Files.readAllBytes(file.toPath());

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename = " + fileName);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.getOutputStream().write(data);

        file.delete();
    }

    private void assertThatFilesAreValid(MultipartFile[] files) {
        if (files.length > 0) {
            for (MultipartFile file : files) {
                if (!file.getContentType().startsWith(AnswerProperties.AUDIO) && !file.getContentType().startsWith(AnswerProperties.IMAGE)) {
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
//                                          @RequestParam(value = "fieldValue", required = false) String fieldValue,
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
//        if (longitude != null) {
//            List<AttachmentDTO.Coordinates> coordinates = new ArrayList<>();
//            for (int i = 0; i < longitude.size(); i++) {
//                AttachmentDTO.Coordinates coord = new AttachmentDTO.Coordinates();
//                coord.longitude = longitude.get(i);
//                coord.latitude = latitude.get(i);
//                coord.altitude = altitude.get(i);
//                coordinates.add(coord);
//            }
//            form.coordinates = coordinates;
//        }
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
