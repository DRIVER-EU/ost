package pl.com.itti.app.driver.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.com.itti.app.driver.dto.ImportExcelTrialAnswerDTO;
import pl.com.itti.app.driver.dto.ImportExcelTrialDTO;
import pl.com.itti.app.driver.dto.ImportExcelTrialPositionDTO;
import pl.com.itti.app.driver.model.enums.AnswerType;
import pl.com.itti.app.driver.model.enums.ErrorLevel;
import pl.com.itti.app.driver.util.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ExcelReadToDtoService {

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
  public static final int MAX_NUMBER_OF_ANSWERS = 10;

  @Autowired
  ExcelImportService excelImportService;


  public ImportExcelTrialDTO readExcelAndReturnDTO(MultipartFile multipartFile) throws IOException {
    Workbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
    Sheet sheet = workbook.getSheet("Questions");
    if(sheet==null)
    {
      throw new ReadingToDTOExcelException("Excel file doesn't contain the sheet named Questions", ErrorLevel.FATAL);
    }
    ImportExcelTrialDTO importExcelTrialDTO = convertExcelToDto(sheet);
    workbook.close();
    return importExcelTrialDTO;
  }

  public ApiJsonAnswer validateImportedContent(ImportExcelTrialDTO importExcelTrialDTO) {
    ApiJsonAnswer apiJsonAnswer = new ApiJsonAnswer();

    //Warnings
    List<ApiValidationWarning> apiValidationWarnings = new ArrayList<>();
    if (importExcelTrialDTO.getTrialName().isEmpty()) {
      apiValidationWarnings.add(new ApiValidationWarning("Trial name is not defined", ErrorLevel.WARN));
    }

    if (importExcelTrialDTO.getTrialPositions().size() == 0) {
      apiValidationWarnings.add(new ApiValidationWarning("Trail contains no questions", ErrorLevel.WARN));
    }

    for (ImportExcelTrialPositionDTO importExcelTrialPositionDTO : importExcelTrialDTO.getTrialPositions()) {
      if (importExcelTrialPositionDTO.getStageName().isEmpty()) {
        apiValidationWarnings.add(new ApiValidationWarning("Empty stage name at question setId = " + importExcelTrialPositionDTO.getQuestionSetId(), ErrorLevel.WARN));
      }

      if (importExcelTrialPositionDTO.getExcelAnswers().size() == 0 && importExcelTrialPositionDTO.getRequired() == Boolean.TRUE) {
        apiValidationWarnings.add(new ApiValidationWarning("Answers not defined question setId = " + importExcelTrialPositionDTO.getQuestionSetId(), ErrorLevel.WARN));
      }

      //Errors
//      List<ApiValidationError> apiValidationErrors = new ArrayList<>();
//      if (true) {
//        apiValidationErrors.add(new ApiValidationError("Test Error", ErrorLevel.FATAL));
//      }

      //TODO JKW discuss further constraints
      apiJsonAnswer.setWarnings(apiValidationWarnings);
//      apiJsonAnswer.setErrors(apiValidationErrors);
    }

    return apiJsonAnswer;
  }

  private ImportExcelTrialDTO convertExcelToDto(Sheet sheet) {

    String trialName = sheet.getRow(1).getCell(TRIAL_NAME).getRichStringCellValue().toString();
    if (trialName.trim().isEmpty()) {
      throw new ReadingToDTOExcelException("The trial name can not be empty or null - 2nd row 2 column", ErrorLevel.FATAL);
    }
    ImportExcelTrialDTO importExcelTrialDTO = ImportExcelTrialDTO.builder()
            .trialName(trialName)
            .build();

    List<ImportExcelTrialPositionDTO> importExcelTrialPositionDTOList = new ArrayList<>();

    for (Row row : sheet) {
      if (row.getRowNum() == 0) {
        continue;
      }
      ImportExcelTrialPositionDTO importExcelTrialPositionDTO = convertPosition(row);
      importExcelTrialPositionDTO.setExcelAnswers(convertAnswers(row));
      importExcelTrialPositionDTO.setJsonSchema(createJsonSchema(importExcelTrialPositionDTO));
      importExcelTrialPositionDTOList.add(importExcelTrialPositionDTO);
    }
    importExcelTrialDTO.setTrialPositions(importExcelTrialPositionDTOList);
    return importExcelTrialDTO;
  }

  private ImportExcelTrialPositionDTO convertPosition(Row row) {

    try {
      ImportExcelTrialPositionDTO importExcelTrialPositionDTO = ImportExcelTrialPositionDTO.builder()
              .questionSetId((long) row.getCell(QUESTION_SET_ID).getNumericCellValue())
              .stageName(row.getCell(STAGE_NAME).getRichStringCellValue().toString())
              .roleName(row.getCell(ROLE_NAME).getRichStringCellValue().toString())
              .question(row.getCell(QUESTION).getRichStringCellValue().toString())
              .description(row.getCell(DESCRIPTION).getRichStringCellValue().toString())
              .dimension(row.getCell(DIMENSION).getRichStringCellValue().toString())
              .position((int) row.getCell(POSITION).getNumericCellValue())
              .required(getBooleanValueFromInt(row.getCell(REQUIRED).getNumericCellValue()))
              .answerType(row.getCell(ANSWER_TYPE).getRichStringCellValue().toString())
              .comments((int) row.getCell(COMMENTS).getNumericCellValue())
              .build();
      return importExcelTrialPositionDTO;
    } catch (Exception e) {
      throw new ExcelImportException("Invalid data Type inside Excel document - please check data-types of Excel Colums", row, e);
    }
  }

  private boolean getBooleanValueFromInt(double doubleValue) {
    if ((int) doubleValue != 0 && (int) doubleValue != 1) {
      throw new ExcelImportException("Invalid data Type inside Excel document - values 0 or 1 expected but got " + doubleValue);
    }
    return (int) doubleValue != 0;
  }

  private List<ImportExcelTrialAnswerDTO> convertAnswers(Row row) {
    List<ImportExcelTrialAnswerDTO> listOfQuestion = new ArrayList<>();
    int position = 1;
    try {
      for (Cell cell : row) {
        if (cell.getColumnIndex() <= MAX_NUMBER_OF_ANSWERS) {
          continue;
        }
        String cellValue = cell.getRichStringCellValue().getString().trim();
        if (!cellValue.isEmpty()) {
          ImportExcelTrialAnswerDTO importExcelTrialAnswerDTO = new ImportExcelTrialAnswerDTO();
          importExcelTrialAnswerDTO.setPosition(position++);
          importExcelTrialAnswerDTO.setDescription(cellValue);
          listOfQuestion.add(importExcelTrialAnswerDTO);
        }
      }
      return listOfQuestion;
    } catch (Exception e) {
      throw new ExcelImportException("Error by converting excel to answers at position = " + position, row, e);
    }
  }

  private String createJsonSchema(ImportExcelTrialPositionDTO importExcelTrialPositionDTO) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      ObjectNode objectNode = objectMapper.createObjectNode();
      objectNode.put("title", importExcelTrialPositionDTO.getQuestion());
      objectNode.put("description", importExcelTrialPositionDTO.getDescription());
      String answerType = importExcelTrialPositionDTO.getAnswerType();
      String enumString = "";
      String jsonStructureToString = "";
      if (answerType.equals("RADIO_BUTTON")) {
        objectNode.put("type", "string");
        enumString = importExcelTrialPositionDTO.getExcelAnswers()
                .stream()
                .map(ImportExcelTrialAnswerDTO::getDescription)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining("\",\"", "\"enum\":[\"", "\"]}"));
        jsonStructureToString = objectNode.toString().replace("}", ",");
      } else if (answerType.equals("TEXT_FIELD")) {
        objectNode.put("type", "string");
        jsonStructureToString = objectNode.toString();
      } else if (answerType.equals("CHECKBOX")) {
        objectNode.put("type", "boolean");
        jsonStructureToString = objectNode.toString();
      } else if (answerType.equals("SLIDER")) {
        objectNode.put("type", "number");
        jsonStructureToString = objectNode.toString();
      } else {
        objectNode.put("type", importExcelTrialPositionDTO.getAnswerType());
      jsonStructureToString = objectNode.toString();
      }
      return jsonStructureToString + enumString;
    } catch (Exception e) {
      throw new ExcelImportException("Error by creating JSON for the Position ", importExcelTrialPositionDTO, e);
    }
  }
}


