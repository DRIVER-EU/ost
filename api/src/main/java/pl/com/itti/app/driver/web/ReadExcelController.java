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
import pl.com.itti.app.driver.model.enums.ErrorLevel;
import pl.com.itti.app.driver.service.ExcelImportService;
import pl.com.itti.app.driver.service.ExcelReadToDtoService;
import pl.com.itti.app.driver.util.*;

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
    public ResponseEntity readExcelAndReturnResponse(@RequestParam("multipartFile") MultipartFile multipartFile) throws IOException {

         String step= "";
        ApiJsonAnswer apiJsonAnswer = new ApiJsonAnswer();
        {
            try {
                step = " @Reading the excel file";
                ImportExcelTrialDTO excelDTO = excelReadToDtoService.readExcelAndReturnDTO(multipartFile);
                step = " @Validating  the excel file";
                apiJsonAnswer = excelReadToDtoService.validateImportedContent(excelDTO);
                step = " @Saving  the excel file";
                Trial savedTrial = excelImportService.saveTrial(excelDTO);
                step = " @Creating response";

                apiJsonAnswer.setId(savedTrial.getId());
                apiJsonAnswer.setName(savedTrial.getName());
                apiJsonAnswer.setDescription(savedTrial.getDescription());

                if(apiJsonAnswer.getErrors().size() == 0)
                {
                    apiJsonAnswer.setStatus(HttpStatus.CREATED);
                }
                else
                {
                    apiJsonAnswer.setStatus(HttpStatus.EXPECTATION_FAILED);
                    throw new  ReadingToDTOExcelException("Validation errors occured", ErrorLevel.ERROR);
                }

            }
            catch (ReadingToDTOExcelException readingToDTOExcelException)
            {
                ApiValidationError apiValidationError = readingToDTOExcelException.getApiValidationError();
                apiValidationError.setMessage(step + " " +apiValidationError.getMessage());
                apiJsonAnswer.getErrors().add(apiValidationError);
                apiJsonAnswer.setStatus(HttpStatus.EXPECTATION_FAILED);
            }
            catch (ExcelImportException excelImportException)
            {
                for (ApiValidationError error : excelImportException.getApiValidationError())
                {
                    apiJsonAnswer.getErrors().add(error);
                }

                apiJsonAnswer.setStatus(HttpStatus.EXPECTATION_FAILED);
            }

            catch (Exception e)
            {
                ApiValidationError apiError = new ApiValidationError();
                apiError.setMessage("Errors in service."+ step + " " + e.getMessage());
                apiJsonAnswer.getErrors().add(apiError);
                apiJsonAnswer.setStatus(HttpStatus.BAD_REQUEST);
            }
        }

        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body(apiJsonAnswer);
        return responseEntity;
    }

    @GetMapping("/import-validate")
    public ImportExcelTrialDTO convertToDTO(@RequestParam int sheetNoToRead, MultipartFile multipartFile) throws IOException {
        ImportExcelTrialDTO excelDTO = excelReadToDtoService.readExcelAndReturnDTO( multipartFile);
        excelReadToDtoService.validateImportedContent(excelDTO);
        return excelDTO;
    }


}


