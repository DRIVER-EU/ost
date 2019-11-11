package pl.com.itti.app.driver.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import pl.com.itti.app.driver.dto.ImportExcelTrialDTO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RunWith(SpringRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@OverrideAutoConfiguration(enabled = false)
@org.springframework.transaction.annotation.Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@ImportAutoConfiguration
public class ExcelReadToDTOTests {

    @Autowired
    private ExcelReadToDtoService readExcelController;

    @Test
    public void compareTrialNameFromExcel() throws IOException {
        // given
        int sheetNoToRead = 4;
        String fileName = "src/test/resources/Trial3_questions_CP_OST_20190911_2.xlsx";
        MultipartFile multipartFile = new MockMultipartFile("Trial3_questions_CP_OST_20190911_2.xlsx", new FileInputStream(new File(fileName)));

        // when
        ImportExcelTrialDTO importExcelTrialDTO = readExcelController.readExcelAndReturnDTO(sheetNoToRead, multipartFile);

        // then
        Assert.assertEquals("Trial Austria", importExcelTrialDTO.getTrialName());
    }

    @Test
    public void compareAnswerTypeOfParsedExcelDTO() throws IOException {
        // given
        int sheetNoToRead = 4;
        String fileName = "src/test/resources/Trial3_questions_CP_OST_20190911_2.xlsx";
        MultipartFile multipartFile = new MockMultipartFile("Trial3_questions_CP_OST_20190911_2.xlsx", new FileInputStream(new File(fileName)));

        // when
        ImportExcelTrialDTO importExcelTrialDTO = readExcelController.readExcelAndReturnDTO(sheetNoToRead, multipartFile);

        // then
        Assert.assertEquals("RADIO_BUTTON", importExcelTrialDTO.getTrialPositions().get(1).getAnswerType());
    }

    @Test
    public void compareNumberOfDtoObjectsReadFromExcel() throws IOException {
        // given
        int sheetNoToRead = 4;
        String fileName = "src/test/resources/Trial3_questions_CP_OST_20190911_2.xlsx";
        MultipartFile multipartFile = new MockMultipartFile("Trial3_questions_CP_OST_20190911_2.xlsx", new FileInputStream(new File(fileName)));

        // when
        ImportExcelTrialDTO importExcelTrialDTO = readExcelController.readExcelAndReturnDTO(sheetNoToRead, multipartFile);

        // then
        Assert.assertEquals(406, importExcelTrialDTO.getTrialPositions().size());
    }

    @Test
    public void readJsonObject() throws IOException {
        // given
        int sheetNoToRead = 4;
        String fileName = "src/test/resources/Trial3_questions_CP_OST_20190911_2.xlsx";
        MultipartFile multipartFile = new MockMultipartFile("Trial3_questions_CP_OST_20190911_2.xlsx", new FileInputStream(new File(fileName)));

        // when
        ImportExcelTrialDTO importExcelTrialDTO = readExcelController.readExcelAndReturnDTO(sheetNoToRead, multipartFile);

        // then
        Assert.assertEquals("{\"title\":\"How much would you agree with the statement that you " +
                        "have experience and knowledge regarding volunteer management?\",\"description\":\"\",\"type\":\"string\"," +
                        "\"enum\":[\"Strongly agree\",\"Agree\",\"Neutral\",\"Disagree\",\"Strongly disagree\",\"Not applicable\"]}",
                importExcelTrialDTO.getTrialPositions().get(1).getJsonSchema());
        Assert.assertEquals("{\"title\":\"Can you observe, the S2-S3 is using the information received by the DLR.\",\"description\":\"\"," +
                        "\"type\":\"boolean\"}",
                importExcelTrialDTO.getTrialPositions().get(51).getJsonSchema());
        Assert.assertEquals("{\"title\":\"Comments (optional)\",\"description\":\"\",\"type\":\"string\"}",
                importExcelTrialDTO.getTrialPositions().get(297).getJsonSchema());
    }
}
