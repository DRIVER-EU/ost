package eu.fp7.driver.ost.driver.service;

import eu.fp7.driver.ost.driver.dto.ImportExcelTrialDTO;
import eu.fp7.driver.ost.driver.model.enums.ErrorLevel;
import eu.fp7.driver.ost.driver.util.ApiJsonAnswer;
import eu.fp7.driver.ost.driver.util.ApiValidationWarning;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@OverrideAutoConfiguration(enabled = false)
@org.springframework.transaction.annotation.Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@ImportAutoConfiguration
public class ExcelReadToDtoServiceTest {

    @Autowired
    private ExcelReadToDtoService excelReadToDtoService;

    @Test
    public void validateImportedContent() throws IOException {
        //given
        MultipartFile multipartFile = new MockMultipartFile("multipartFile", "Trial3_questions_CP_OST_20190911_2_errorTest.xlsx", "application/vnd.ms-excel", new ClassPathResource("Trial3_questions_CP_OST_20190911_2_errorTest.xlsx").getInputStream());
        List<ApiValidationWarning> apiValidationWarningList = new ArrayList<>();
        apiValidationWarningList.add(new ApiValidationWarning("Answers not defined question setId = 2", ErrorLevel.WARN));

        //when
        ImportExcelTrialDTO importExcelTrialDTO = excelReadToDtoService.readExcelAndReturnDTO(multipartFile);
        ApiJsonAnswer apiJsonAnswer = excelReadToDtoService.validateImportedContent(importExcelTrialDTO);

        //then
        Assert.assertEquals(apiJsonAnswer.getWarnings(), apiValidationWarningList);
    }

    @Test
    public void readExcelAndReturnDTO() throws IOException {
        //given
        MultipartFile file = new MockMultipartFile("multipartFile", "Trial3_questions_CP_OST_20190911_2.xlsx", "application/vnd.ms-excel", new ClassPathResource("Trial3_questions_CP_OST_20190911_2_errorTest.xlsx").getInputStream());

        //when
        ImportExcelTrialDTO importExcelTrialDTO = excelReadToDtoService.readExcelAndReturnDTO(file);

        //then
        Assert.assertNotNull(importExcelTrialDTO);
    }
}