package pl.com.itti.app.driver.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.com.itti.app.driver.dto.ImportExcelTrialDTO;
import pl.com.itti.app.driver.model.Trial;
import pl.com.itti.app.driver.service.ExcelImportService;
import pl.com.itti.app.driver.service.ExcelReadToDtoService;

import java.io.IOException;


@RestController
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


    @GetMapping("/import")
    public Trial readExcelAndReturnDTOEndpoint(@RequestParam int sheetNoToRead, @RequestParam("multipartFile") MultipartFile multipartFile) throws IOException {
        ImportExcelTrialDTO excelDTO = excelReadToDtoService.readExcelAndReturnDTO(sheetNoToRead, multipartFile);
        Trial savedTrial = excelImportService.saveTrial(excelDTO);
        return savedTrial;
    }

    @GetMapping("/import-validate")
    public ImportExcelTrialDTO convertToDTO(@RequestParam int sheetNoToRead, MultipartFile multipartFile) throws IOException {
        ImportExcelTrialDTO excelDTO = excelReadToDtoService.readExcelAndReturnDTO(sheetNoToRead, multipartFile);
        excelReadToDtoService.validateImportedContent(excelDTO);
        return excelDTO;
    }


}


