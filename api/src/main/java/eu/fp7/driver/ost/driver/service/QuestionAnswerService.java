package eu.fp7.driver.ost.driver.service;

import eu.fp7.driver.ost.core.exception.EntityNotFoundException;
import eu.fp7.driver.ost.driver.model.Answer;
import eu.fp7.driver.ost.driver.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class QuestionAnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private TrialUserService trialUserService;

    @Transactional(readOnly = true)
    public Answer getByAnswerId(long answerId) {
        return answerRepository.findById(answerId)
                .orElseThrow(() -> new EntityNotFoundException(Answer.class, answerId));
    }

    public void addComment(long answerId, String comment) {
        Answer answer = getByAnswerId(answerId);
        answer.setComment(comment);

        answerRepository.save(answer);
    }
}
