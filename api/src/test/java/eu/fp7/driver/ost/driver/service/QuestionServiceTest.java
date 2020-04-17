//package eu.fp7.driver.ost.driver.service;
//
//import eu.fp7.driver.ost.driver.dto.AdminQuestionDTO;
//import eu.fp7.driver.ost.driver.dto.AdminQuestionOptionDTO;
//import eu.fp7.driver.ost.driver.dto.ImportExcelTrialDTO;
//import eu.fp7.driver.ost.driver.model.Question;
//import eu.fp7.driver.ost.driver.model.QuestionOption;
//import eu.fp7.driver.ost.driver.model.TrialSession;
//import eu.fp7.driver.ost.driver.model.enums.AnswerType;
//import eu.fp7.driver.ost.driver.repository.QuestionOptionRepository;
//import eu.fp7.driver.ost.driver.repository.QuestionRepository;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
//import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
//import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
//import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
//import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.context.BootstrapWith;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//@BootstrapWith(SpringBootTestContextBootstrapper.class)
//@OverrideAutoConfiguration(enabled = false)
//@org.springframework.transaction.annotation.Transactional
//@AutoConfigureCache
//@AutoConfigureDataJpa
//@ImportAutoConfiguration
//public class QuestionServiceTest {
//
//    @Autowired
//    QuestionService questionService;
//
//    @Autowired
//    QuestionRepository questionRepository;
//
//    @Autowired
//    QuestionOptionService questionOptionService;
//
//
//    @Test
//    public void createJsonSchemaFromOptionsRadioButton() {
//        final int QUESTION_OPTION_AMOUNT = 3;
//        // given
//        AdminQuestionDTO.FullItem adminQuestionDTOFullItem = new AdminQuestionDTO.FullItem();
//        adminQuestionDTOFullItem.setName("TestTitle");
//        adminQuestionDTOFullItem.setDescription("TestDescription");
//        adminQuestionDTOFullItem.setAnswerType(AnswerType.RADIO_BUTTON);
//
//        List<AdminQuestionOptionDTO.ListItem> adminQuestionOptionDTOList = new ArrayList<>();
//        for(int i=0;i<QUESTION_OPTION_AMOUNT;i++) {
//            AdminQuestionOptionDTO.ListItem listItem = new AdminQuestionOptionDTO.ListItem();
//            listItem.setName("TestQuestionOption"+(i+1));
//            listItem.setPosition(i+1);
//            adminQuestionOptionDTOList.add(listItem);
//            adminQuestionDTOFullItem.setQuestionOptions(adminQuestionOptionDTOList);
//        }
//        adminQuestionDTOFullItem.setQuestionOptions(adminQuestionOptionDTOList);
//
//        // when
//        String jsonSchemaFromOptions = questionService.createJsonSchemaFromOptions(adminQuestionDTOFullItem);
//
//        // then
//        Assert.assertEquals("{\"title\":\"TestTitle\",\"description\":\"TestDescription\",\"type\":\"string\"," +
//                "\"enum\":[\"TestQuestionOption1\",\"TestQuestionOption2\",\"TestQuestionOption3\"],\"required\":true}",
//                jsonSchemaFromOptions);
//    }
//
//    @Test
//    public void createJsonSchemaFromOptionsTextField() {
//        final int QUESTION_OPTION_AMOUNT = 3;
//        // given
//        AdminQuestionDTO.FullItem adminQuestionDTOFullItem = new AdminQuestionDTO.FullItem();
//        adminQuestionDTOFullItem.setName("TestTitle");
//        adminQuestionDTOFullItem.setDescription("TestDescription");
//        adminQuestionDTOFullItem.setAnswerType(AnswerType.TEXT_FIELD);
//
//        List<AdminQuestionOptionDTO.ListItem> adminQuestionOptionDTOList = new ArrayList<>();
//        adminQuestionDTOFullItem.setQuestionOptions(adminQuestionOptionDTOList);
//
//        // when
//        String jsonSchemaFromOptions = questionService.createJsonSchemaFromOptions(adminQuestionDTOFullItem);
//
//        // then
//        Assert.assertEquals("{\"title\":\"TestTitle\",\"description\":\"TestDescription\",\"type\":\"string\"}",
//                jsonSchemaFromOptions);
//    }
//
//    @Test
//    public void createJsonSchemaFromOptionsCheckbox() {
//        final int QUESTION_OPTION_AMOUNT = 3;
//        // given
//        AdminQuestionDTO.FullItem adminQuestionDTOFullItem = new AdminQuestionDTO.FullItem();
//        adminQuestionDTOFullItem.setName("TestTitle");
//        adminQuestionDTOFullItem.setDescription("TestDescription");
//        adminQuestionDTOFullItem.setAnswerType(AnswerType.CHECKBOX);
//
//        List<AdminQuestionOptionDTO.ListItem> adminQuestionOptionDTOList = new ArrayList<>();
//        for(int i=0;i<QUESTION_OPTION_AMOUNT;i++) {
//            AdminQuestionOptionDTO.ListItem listItem = new AdminQuestionOptionDTO.ListItem();
//            listItem.setName("TestQuestionOption"+(i+1));
//            listItem.setPosition(i+1);
//            adminQuestionOptionDTOList.add(listItem);
//            adminQuestionDTOFullItem.setQuestionOptions(adminQuestionOptionDTOList);
//        }
//        adminQuestionDTOFullItem.setQuestionOptions(adminQuestionOptionDTOList);
//
//        // when
//        String jsonSchemaFromOptions = questionService.createJsonSchemaFromOptions(adminQuestionDTOFullItem);
//
//        // then
//        Assert.assertEquals("{\"type\":\"array\",\"title\":\"TestTitle\",\"description\":\"TestDescription\"," +
//                        "\"items\":{\"type\":\"string\",\"enum\":[\"TestQuestionOption1\",\"TestQuestionOption2\"," +
//                        "\"TestQuestionOption3\"]},\"uniqueItems\":true}",
//                jsonSchemaFromOptions);
//    }
//
//    @Test
//    public void createJsonSchemaFromOptionsSlider() {
//        // given
//        AdminQuestionDTO.FullItem adminQuestionDTOFullItem = new AdminQuestionDTO.FullItem();
//        adminQuestionDTOFullItem.setName("TestTitle");
//        adminQuestionDTOFullItem.setDescription("TestDescription");
//        adminQuestionDTOFullItem.setAnswerType(AnswerType.SLIDER);
//
//        List<AdminQuestionOptionDTO.ListItem> adminQuestionOptionDTOList = new ArrayList<>();
//        adminQuestionDTOFullItem.setQuestionOptions(adminQuestionOptionDTOList);
//
//        // when
//        String jsonSchemaFromOptions = questionService.createJsonSchemaFromOptions(adminQuestionDTOFullItem);
//
//        // then
//        Assert.assertEquals("{\"title\":\"TestTitle\",\"description\":\"TestDescription\"," +
//                        "\"type\":\"integer\",\"min\":1,\"max\":10,\"value\":2,\"step\":1,\"required\":false}",
//                jsonSchemaFromOptions);
//    }
//    @Test
//    public void createJsonSchemaFromOptionsRadioLine() {
//        final int QUESTION_OPTION_AMOUNT = 3;
//        // given
//        AdminQuestionDTO.FullItem adminQuestionDTOFullItem = new AdminQuestionDTO.FullItem();
//        adminQuestionDTOFullItem.setName("TestTitle");
//        adminQuestionDTOFullItem.setDescription("TestDescription");
//        adminQuestionDTOFullItem.setAnswerType(AnswerType.RADIO_LINE);
//
//        List<AdminQuestionOptionDTO.ListItem> adminQuestionOptionDTOList = new ArrayList<>();
//        for(int i=0;i<QUESTION_OPTION_AMOUNT;i++) {
//            AdminQuestionOptionDTO.ListItem listItem = new AdminQuestionOptionDTO.ListItem();
//            listItem.setName("TestQuestionOption"+(i+1));
//            listItem.setPosition(i+1);
//            adminQuestionOptionDTOList.add(listItem);
//            adminQuestionDTOFullItem.setQuestionOptions(adminQuestionOptionDTOList);
//        }
//        adminQuestionDTOFullItem.setQuestionOptions(adminQuestionOptionDTOList);
//
//        // when
//        String jsonSchemaFromOptions = questionService.createJsonSchemaFromOptions(adminQuestionDTOFullItem);
//
//        // then
//        Assert.assertEquals("{\"type\":\"string\",\"title\":\"TestTitle\",\"description\":\"TestDescription\"," +
//                        "\"enum\":[\"TestQuestionOption1\",\"TestQuestionOption2\",\"TestQuestionOption3\"]," +
//                        "\"required\":true}",
//                jsonSchemaFromOptions);
//    }
//
//    @Test
//    public void insert() {
//        final int QUESTION_OPTION_AMOUNT = 3;
//        //given
//        AdminQuestionDTO.FullItem adminQuestionDTOFullItem = new AdminQuestionDTO.FullItem();
//        adminQuestionDTOFullItem.setObservationTypeId(8);
//        adminQuestionDTOFullItem.setName("TestTitle");
//        adminQuestionDTOFullItem.setAnswerType(AnswerType.RADIO_BUTTON);
//        adminQuestionDTOFullItem.setDescription("TestDescription");
//        adminQuestionDTOFullItem.setCommented(true);
//        adminQuestionDTOFullItem.setPosition(8);
//        adminQuestionDTOFullItem.setJsonSchema("{}");
//        Question question = questionService.insert(adminQuestionDTOFullItem);
//
//        List<AdminQuestionOptionDTO.ListItem> adminQuestionOptionDTOList = new ArrayList<>();
//        for(int i=0;i<QUESTION_OPTION_AMOUNT;i++) {
//            AdminQuestionOptionDTO.FullItem listItem = new AdminQuestionOptionDTO.FullItem();
//            listItem.setName("TestQuestionOption"+(i+1));
//            listItem.setPosition(i+1);
//            listItem.questionId = question.getId();
//            adminQuestionOptionDTOList.add(listItem);
//            adminQuestionDTOFullItem.setQuestionOptions(adminQuestionOptionDTOList);
//            QuestionOption questionOption = questionOptionService.insert(listItem);
//            question.getQuestionOptions().add(questionOption);
//        }
//        adminQuestionDTOFullItem.setQuestionOptions(adminQuestionOptionDTOList);
//
//        //when
//        questionService.updateJson(question.getId());
//
//        //then
//        Optional<Question> questionRepositoryById = questionRepository.findById(question.getId());
//        Assert.assertEquals(questionRepositoryById.get().getName(), adminQuestionDTOFullItem.getName());
//    }
//}