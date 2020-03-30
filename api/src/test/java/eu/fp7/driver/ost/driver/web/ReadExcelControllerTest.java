//package eu.fp7.driver.ost.driver.web;
//
//import eu.fp7.driver.ost.driver.dto.ImportExcelTrialDTO;
//import eu.fp7.driver.ost.driver.service.ExcelReadToDtoService;
//import org.junit.Assert;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//
//import static org.junit.Assert.*;
//
//public class ReadExcelControllerTest {
//    @Autowired
//    ReadExcelController readExcelController;
//
//    @Test
//    public void readExcelAndReturnResponceMock() {
//    }
//
//    @Test
//    public void readExcelAndReturnResponse() throws IOException {
//        //given
//        MultipartFile multipartFile= new MockMultipartFile("multipartFile", "Trial3_questions_CP_OST_20190911_2.xlsx", "application/vnd.ms-excel", new ClassPathResource("Trial3_questions_CP_OST_20190911_2_errorTest.xlsx").getInputStream());
//
//        //when
//        ResponseEntity responseEntity = readExcelController.readExcelAndReturnResponse(multipartFile);
//
//        //then
//        Assert.assertNotNull(responseEntity);
//    }
//}