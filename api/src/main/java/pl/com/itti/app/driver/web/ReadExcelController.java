package pl.com.itti.app.driver.web;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.com.itti.app.driver.dto.ImportExcelTrialDTO;
import pl.com.itti.app.driver.model.Trial;
import pl.com.itti.app.driver.model.TrialRole;
import pl.com.itti.app.driver.model.TrialSession;
import pl.com.itti.app.driver.model.TrialStage;
import pl.com.itti.app.driver.service.ExcelImportService;
import pl.com.itti.app.driver.service.ExcelReadToDtoService;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ReadExcelController {

    public static final int QUESTION_SET_ID = 0;
    public static final int TRIAL_NAME = 1;
    public static final int STAGE_NAME = 2;
    public static final int ROLE_NAME = 3;
    public static final int QUESTION = 4;
    public static final int DESCRIPTION = 5;
    public static final int DIMENSION = 6;
    public static final int POSITION = 7;
    public static final int REQUIRED = 8;
    public static final int ANSWER_TYPE = 9;
    public static final int COMMENTS = 10;

    @Autowired
    ExcelImportService excelImportService;

    @Autowired
    ExcelReadToDtoService excelReadToDtoService;


    @GetMapping("/importOld")
    public Trial readExcelAndReturnDTOEndpoint(@RequestParam int sheetNoToRead, @RequestParam("multipartFile") MultipartFile multipartFile) throws IOException {
        ImportExcelTrialDTO excelDTO = excelReadToDtoService.readExcelAndReturnDTO(multipartFile);
        Trial savedTrial = excelImportService.saveTrial(excelDTO);
        return savedTrial;
    }

    @PostMapping("/importMock")
    public ResponseEntity readExcelAndReturnResponceMock(@RequestParam int sheetNoToRead) throws IOException {

        JSONObject jsonAnswer = new JSONObject();

        {


                jsonAnswer.put("id","1");
                jsonAnswer.put("name","Name - Mock");
                jsonAnswer.put("description","description");
                jsonAnswer.put("status",HttpStatus.CREATED);
                jsonAnswer.put("errors",new JSONArray());


        }


        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body(jsonAnswer.toString());

        return responseEntity;
    }

    @PutMapping("/import")
    public ResponseEntity readExcelAndReturnResponce(@RequestParam("multipartFile") MultipartFile multipartFile) throws IOException {
         JSONObject jsonAnswer = new JSONObject();
         String step= "";

        {
            try {
                step = " @Reading the excel file";
                ImportExcelTrialDTO excelDTO = excelReadToDtoService.readExcelAndReturnDTO(multipartFile);
                step = " @Validating  the excel file";
                JSONArray validationWarningList = excelReadToDtoService.validateImportedContent(excelDTO);
                step = " @Saving  the excel file";
                Trial savedTrial = excelImportService.saveTrial(excelDTO);
                step = " @Creating response";
                jsonAnswer.put("id",savedTrial.getId().toString());
                jsonAnswer.put("name",savedTrial.getName());
                jsonAnswer.put("description",savedTrial.getDescription());

                jsonAnswer.put("stages", getJSONTrailArray(savedTrial.getTrialStages()));
                jsonAnswer.put("sessions",getJSONSessionArray(savedTrial.getTrialSessions()));
                jsonAnswer.put("roles",getJSONTRolesArray(savedTrial.getTrialRoles()));

                jsonAnswer.put("status",HttpStatus.CREATED);
                jsonAnswer.put("errors",new JSONArray());
                if(validationWarningList.length()>0) {
                    jsonAnswer.put("warnings",validationWarningList);
                }
            }
            catch (Exception e)
            {
                JSONArray serverErrorList = new JSONArray();
                JSONObject serverError = new JSONObject();
                serverError.put("serviceError", "Errors in service."+ step + " " + e.getMessage());
                serverErrorList.put(serverError);
                jsonAnswer.put("status",HttpStatus.BAD_REQUEST);
                jsonAnswer.put("errors",serverErrorList);
            }

        }

        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body(jsonAnswer.toString());

        return responseEntity;
    }

    private JSONArray getJSONTRolesArray(List<TrialRole> trialRoles) {
        JSONArray jsonRoleslList = new JSONArray();
        trialRoles.forEach(trialRole -> {
            JSONObject jsonAnswer = new JSONObject();
            jsonAnswer.put("id",trialRole.id);
            jsonAnswer.put("name",trialRole.getName());

            jsonRoleslList.put(jsonAnswer);
        });
        return jsonRoleslList;
    }
    private JSONArray getJSONSessionArray(List<TrialSession> trialSessions) {
        JSONArray jsonSessionList = new JSONArray();
        //TODO JKW delete it just for tests

        JSONObject jsonAnswerMock = new JSONObject();
        jsonAnswerMock.put("id",0);
        jsonAnswerMock.put("name","Mock for session");
        jsonSessionList.put(jsonAnswerMock);

        trialSessions.forEach(trialSession -> {
            JSONObject jsonAnswer = new JSONObject();
            jsonAnswer.put("id",trialSession.id);
            jsonAnswer.put("name",trialSession.id);
            jsonSessionList.put(jsonAnswer);
        });
        return jsonSessionList;
    }
    private JSONArray getJSONTrailArray(List<TrialStage> trialStages) {
        JSONArray jsonTrialStagesList = new JSONArray();

        trialStages.forEach(trialStage -> {
            JSONObject jsonAnswer = new JSONObject();
            jsonAnswer.put("id",trialStage.id);
            jsonAnswer.put("name",trialStage.getName());
            jsonTrialStagesList.put(jsonAnswer);
        });
        return jsonTrialStagesList;
    }

    @GetMapping("/import-validate")
    public ImportExcelTrialDTO convertToDTO(@RequestParam int sheetNoToRead, MultipartFile multipartFile) throws IOException {
        ImportExcelTrialDTO excelDTO = excelReadToDtoService.readExcelAndReturnDTO( multipartFile);
        excelReadToDtoService.validateImportedContent(excelDTO);
        return excelDTO;
    }


}


