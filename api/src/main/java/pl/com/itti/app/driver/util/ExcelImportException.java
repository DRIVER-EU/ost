package pl.com.itti.app.driver.util;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.com.itti.app.driver.dto.ImportExcelTrialPositionDTO;
import pl.com.itti.app.driver.model.enums.ErrorLevel;

import java.util.ArrayList;
import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)

public class ExcelImportException extends RuntimeException {

    private List<ApiValidationError> apiValidationErrorList = new ArrayList<ApiValidationError>();


    public ExcelImportException(String message, ImportExcelTrialPositionDTO importExcelTrialPositionDTO, Exception e) {
        super(message, e);
        ApiValidationError newError = new ApiValidationError();
        newError.setMessage(e.getMessage());
        apiValidationErrorList.add(newError);
        logIt(importExcelTrialPositionDTO);
    }

    public ExcelImportException(String message, ImportExcelTrialPositionDTO importExcelTrialPositionDTO) {
        super(message);
        ApiValidationError newError = new ApiValidationError();
        newError.setMessage(message);
        newError.setErrorLevel(ErrorLevel.FATAL);
        apiValidationErrorList.add(newError);
        logIt(importExcelTrialPositionDTO);
    }
    public ExcelImportException(String message, Row row, Exception e) {
        super(message, e);
        ApiValidationError newError = new ApiValidationError();
        newError.setMessage(row.toString()+ " "+e.getMessage());
        newError.setErrorLevel(ErrorLevel.FATAL);
        apiValidationErrorList.add(newError);
        logIt(row);
    }

    public ExcelImportException(String message) {
        super(message);
        ApiValidationError newError = new ApiValidationError();
        newError.setMessage(message);
        newError.setErrorLevel(ErrorLevel.FATAL);
        apiValidationErrorList.add(newError);
    }

    public List<ApiValidationError> getApiValidationError() {
        return apiValidationErrorList;
    }

    private <T> void logIt (T element)
    {
        LogService.saveToLogFile(element.toString());
    }

}

