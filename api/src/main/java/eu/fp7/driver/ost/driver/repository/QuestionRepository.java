package eu.fp7.driver.ost.driver.repository;

import eu.fp7.driver.ost.driver.model.Question;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface QuestionRepository
        extends PagingAndSortingRepository<Question, Long>, JpaSpecificationExecutor<Question> {
    Optional<Question> findById(Long id);
}
