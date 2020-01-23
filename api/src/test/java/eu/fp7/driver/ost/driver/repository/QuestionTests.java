package eu.fp7.driver.ost.driver.repository;

import eu.fp7.driver.ost.driver.model.Question;
import eu.fp7.driver.ost.driver.model.enums.AnswerType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@OverrideAutoConfiguration(enabled = false)
@org.springframework.transaction.annotation.Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@ImportAutoConfiguration
public class QuestionTests {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ObservationTypeRepository observationTypeRepository;

    @Test
    public void findOneById() {
        // when
        Question question = questionRepository.findOne(9L);

        // then
        Assert.assertEquals(9L, question.getId().longValue());
    }

    @Test
    public void findAll() {
        // when
        Page<Question> questionPage = questionRepository.findAll(new PageRequest(1,10));

        // then
        Assert.assertEquals(9L, questionPage.getTotalElements());
    }

    @Test
    public void create() {
        // given
        Question question = Question.builder()
                .answerType(AnswerType.CHECKBOX)
                .name("name")
                .description("description")
                .observationType(observationTypeRepository.findOne(1L))
                .jsonSchema("schema")
                .build();

        // when
        Question savedQuestion = questionRepository.save(question);
        Page<Question> questionPage = questionRepository.findAll(new PageRequest(1,10));

        // then
        Assert.assertEquals(10L, questionPage.getTotalElements());
        Assert.assertEquals(question.getAnswerType(), savedQuestion.getAnswerType());
        Assert.assertEquals(question.getName(), savedQuestion.getName());
        Assert.assertEquals(question.getDescription(), savedQuestion.getDescription());
        Assert.assertEquals(question.getObservationType(), savedQuestion.getObservationType());
    }

    @Test
    public void update() {
        // given
        Question question = questionRepository.findOne(9L);
        question.setAnswerType(AnswerType.CHECKBOX);

        // when
        Question savedQuestion = questionRepository.save(question);

        // then
        Assert.assertEquals(AnswerType.CHECKBOX, savedQuestion.getAnswerType());
        Assert.assertEquals(question.getName(), savedQuestion.getName());
        Assert.assertEquals(question.getDescription(), savedQuestion.getDescription());
        Assert.assertEquals(question.getObservationType(), savedQuestion.getObservationType());
    }

    @Test
    public void delete() {
        // when
        questionRepository.delete(9L);
        Page<Question> questionPage = questionRepository.findAll(new PageRequest(1,10));

        // then
        Assert.assertEquals(8L, questionPage.getTotalElements());
    }
}
