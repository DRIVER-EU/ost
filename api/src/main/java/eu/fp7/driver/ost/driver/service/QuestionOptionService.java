package eu.fp7.driver.ost.driver.service;

import co.perpixel.exception.EntityNotFoundException;
import eu.fp7.driver.ost.driver.dto.AdminQuestionOptionDTO;
import eu.fp7.driver.ost.driver.model.Question;
import eu.fp7.driver.ost.driver.model.QuestionOption;
import eu.fp7.driver.ost.driver.repository.QuestionOptionRepository;
import eu.fp7.driver.ost.driver.repository.QuestionRepository;
import eu.fp7.driver.ost.driver.util.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class QuestionOptionService {


    @Autowired
    private QuestionOptionRepository questionOptionRepository;

    @Autowired
    private QuestionRepository questionRepository;


    @Transactional(readOnly = true)
    public QuestionOption findById(Long id) {
        QuestionOption question = questionOptionRepository.findById(id)
                .orElseThrow(() ->  new EntityNotFoundException(QuestionOption.class,  id));
        return question;
    }

    @Transactional
    public void delete(long id) {
        QuestionOption questionOption = questionOptionRepository.findById(id)
                .orElseThrow(() ->  new EntityNotFoundException(QuestionOption.class,  id));
        questionOptionRepository.delete(questionOption);
    }

    @Transactional
    public QuestionOption insert(AdminQuestionOptionDTO.FullItem adminQuestionOptionDTO) {
        if (adminQuestionOptionDTO.getQuestionId() == 0) {
            new InvalidDataException("Data Error missing Question Id in request ");
        }
        Question question = questionRepository.findById(adminQuestionOptionDTO.getQuestionId())
                .orElseThrow(() ->  new EntityNotFoundException(Question.class,  adminQuestionOptionDTO.getQuestionId()));


        QuestionOption questionOption = QuestionOption.builder()
                .name(adminQuestionOptionDTO.getName())
                .position(adminQuestionOptionDTO.getPosition())
                .question(question)
                .build();

        return questionOptionRepository.save(questionOption);

    }

    @Transactional
    public AdminQuestionOptionDTO.FullItem getFullQuestion(Long id) {
        QuestionOption questionOption = questionOptionRepository.findById(id)
                .orElseThrow(() ->  new EntityNotFoundException(QuestionOption.class,  id));
        AdminQuestionOptionDTO.FullItem fullQuestionOption = new AdminQuestionOptionDTO.FullItem();
        fullQuestionOption.toDto(questionOption);
        return fullQuestionOption;
    }

}
