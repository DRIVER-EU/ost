package pl.com.itti.app.driver.service;

import co.perpixel.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.driver.model.Answer;
import pl.com.itti.app.driver.repository.AnswerRepository;

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
