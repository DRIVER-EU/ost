package pl.com.itti.app.driver.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.com.itti.app.driver.model.Answer;

import java.util.List;

@Repository
public interface AnswerRepository
        extends PagingAndSortingRepository<Answer, Long>, JpaSpecificationExecutor<Answer> {

    List<Answer> findAllByTrialSessionId(long trialSessionId);
}
