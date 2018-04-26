package pl.com.itti.app.driver.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.com.itti.app.driver.model.Answer;

public interface AnswerRepository
        extends PagingAndSortingRepository<Answer, Long>, JpaSpecificationExecutor<Answer> {
}
