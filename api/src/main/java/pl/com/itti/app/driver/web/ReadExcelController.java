package pl.com.itti.app.driver.web;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.com.itti.app.driver.dto.ImportExcelTrialAnswerDTO;
import pl.com.itti.app.driver.dto.ImportExcelTrialDTO;
import pl.com.itti.app.driver.dto.ImportExcelTrialPositionDTO;
import pl.com.itti.app.driver.model.Trial;
import pl.com.itti.app.driver.service.ExcelImportService;
import pl.com.itti.app.driver.util.ExcelImportException;
import pl.com.itti.app.driver.util.ReadingToDTOExcelException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
public class ReadExcelController {

    @Autowired
    ExcelImportService excelImportService;


    @GetMapping("/import")
    public Trial readExcelAndReturnDTOEndpoint(@RequestParam int sheetNoToRead, @RequestParam("multipartFile") MultipartFile multipartFile) throws IOException {
        ImportExcelTrialDTO excelDTO = readExcelAndReturnDTO(sheetNoToRead, multipartFile);
        Trial savedTrial = excelImportService.saveTrial(excelDTO);
        return savedTrial;
    }

    @GetMapping("/import-validate")
    public ImportExcelTrialDTO convertToDTO(@RequestParam int sheetNoToRead, MultipartFile multipartFile) throws IOException {
        ImportExcelTrialDTO excelDTO = readExcelAndReturnDTO(sheetNoToRead, multipartFile);
        validateImportedContent(excelDTO);
        return excelDTO;
    }

    public ImportExcelTrialDTO readExcelAndReturnDTO(int sheetNoToRead, MultipartFile multipartFile) throws IOException {
        Workbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
        Sheet sheet = workbook.getSheetAt(sheetNoToRead);
        ImportExcelTrialDTO importExcelTrialDTO = convertExcelToDto(sheet);
        workbook.close();
        return importExcelTrialDTO;
    }

    private ImportExcelTrialDTO convertExcelToDto(Sheet sheet) {

        String trialName = sheet.getRow(1).getCell(1).getRichStringCellValue().toString();
        if (trialName.trim().isEmpty()) {
            throw new ReadingToDTOExcelException("The trial name can not be empty or null - 2nd row 2 column");
        }
        ImportExcelTrialDTO importExcelTrialDTO = ImportExcelTrialDTO.builder()
                .trialName(trialName)
                .build();

        List<ImportExcelTrialPositionDTO> importExcelTrialPositionDTOList = new ArrayList<>();
        int i = 0;
        for (Row row : sheet) {
            if (i++ == 0) {
                //skip 1st line
                continue;
            }
            ImportExcelTrialPositionDTO importExcelTrialPositionDTO = convertPosition(row);
            importExcelTrialPositionDTO.setExcelAnsewrs(convertAnswers(row));
            importExcelTrialPositionDTO.setJsonSchema(createJsonSchema(importExcelTrialPositionDTO));
            importExcelTrialPositionDTOList.add(importExcelTrialPositionDTO);
        }
        importExcelTrialDTO.setTrialPositions(importExcelTrialPositionDTOList);
        return importExcelTrialDTO;
    }

    private ImportExcelTrialPositionDTO convertPosition(Row row) {

        try {
            ImportExcelTrialPositionDTO importExcelTrialPositionDTO = ImportExcelTrialPositionDTO.builder()
                    .questionSetId((long) row.getCell(0).getNumericCellValue())
                    .stageName(row.getCell(2).getRichStringCellValue().toString())
                    .roleName(row.getCell(3).getRichStringCellValue().toString())
                    .question(row.getCell(4).getRichStringCellValue().toString())
                    .description(row.getCell(5).getRichStringCellValue().toString())
                    .dimension(row.getCell(6).getRichStringCellValue().toString())
                    .position((int) row.getCell(7).getNumericCellValue())
                    .requiered(getBooleanValueFromInt(row.getCell(8).getNumericCellValue()))
                    .answerType(row.getCell(9).getRichStringCellValue().toString())
                    .comments((int) row.getCell(10).getNumericCellValue())
                    .build();
            return importExcelTrialPositionDTO;
        } catch (Exception e) {
            throw new ExcelImportException("Invalid data Type inside Excel document - please check data-types of Excel Colums", row, e);
        }

    }

    private boolean getBooleanValueFromInt(double doubleValue) {
        return (int) doubleValue != 0;
    }

    private List<ImportExcelTrialAnswerDTO> convertAnswers(Row row) {
        List<ImportExcelTrialAnswerDTO> listOfQuestion = new ArrayList<>();
        String cellValue;
        int i = 1;
        try {
            for (Cell cell : row) {
                if (cell.getColumnIndex() <= 10) {
                    continue;
                }
                cellValue = cell.getRichStringCellValue().getString().trim();
                if (!cellValue.isEmpty()) {
                    ImportExcelTrialAnswerDTO importExcelTrialAnswerDTO = new ImportExcelTrialAnswerDTO();
                    importExcelTrialAnswerDTO.setPosition(i++);
                    importExcelTrialAnswerDTO.setDescription(cellValue);
                    listOfQuestion.add(importExcelTrialAnswerDTO);
                }
            }
            return listOfQuestion;
        } catch (Exception e) {
            throw new ExcelImportException("Error by converting excel to answers at position = " + i, row, e);
        }
    }

    private String createJsonSchema(ImportExcelTrialPositionDTO importExcelTrialPositionDTO) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("title", importExcelTrialPositionDTO.getQuestion());
            objectNode.put("description", importExcelTrialPositionDTO.getDescription());
            String answerType = importExcelTrialPositionDTO.getAnswerType().toString();
            if (answerType.equals("RADIO_BUTTON")) {
                objectNode.put("type", "string");

                //Create Enum for RADIO_BUTTON
                List<ImportExcelTrialAnswerDTO> excelAnsewrs = importExcelTrialPositionDTO.getExcelAnsewrs();
                //    ObjectNode addedNode = ((ObjectNode) objectNode).putObject("enum");
                ArrayList<String> arrayList = new ArrayList<>();

                for (ImportExcelTrialAnswerDTO importExcelTrialAnswerDTO : excelAnsewrs) {
                //      addedNode.put(importExcelTrialAnswerDTO.getPosition().toString(), importExcelTrialAnswerDTO.getDescription());
                    arrayList.add(importExcelTrialAnswerDTO.getDescription());
                //      addedNode.get(importExcelTrialAnswerDTO.getDescription());
                    objectNode.put("enum", String.valueOf(arrayList));
                }
            } else if (answerType.equals("TEXT_FIELD")) {
                objectNode.put("type", "string");
            } else if (answerType.equals("CHECKBOX")) {
                objectNode.put("type", "boolean");
            } else if (answerType.equals("SLIDER")) {
                objectNode.put("type", "number");
            } else
                objectNode.put("type", importExcelTrialPositionDTO.getAnswerType());
//TODO: MS Handle other Enum Types
            return objectNode.toString();
        } catch (Exception e) {
            throw new ExcelImportException("Error by creating JSON for the Position ", importExcelTrialPositionDTO, e);
        }
    }


    private List<String> validateImportedContent(ImportExcelTrialDTO importExcelTrialDTO) {

        List<String> errorList = new ArrayList<>();
        if (importExcelTrialDTO.getTrialName().isEmpty()) {
            errorList.add("Trial name is not defined");
        }

        if (importExcelTrialDTO.getTrialPositions().size() == 0) {
            errorList.add("Trail contains no questions");
        }

        for (ImportExcelTrialPositionDTO importExcelTrialPositionDTO : importExcelTrialDTO.getTrialPositions()) {
            if (importExcelTrialPositionDTO.getStageName().isEmpty()) {
                errorList.add("Empty stage name at question setId = " + importExcelTrialPositionDTO.getQuestionSetId());
            }

            if (importExcelTrialPositionDTO.getExcelAnsewrs().size() == 0) {
                errorList.add("Answers not defined question setId = " + importExcelTrialPositionDTO.getQuestionSetId());
            }

            //TODO JKW discuss further constraints

        }
        if (errorList.size() > 0) {
            throw new ExcelImportException("Excel failed wit the content validation", errorList);
        }
        return errorList;
    }
}


