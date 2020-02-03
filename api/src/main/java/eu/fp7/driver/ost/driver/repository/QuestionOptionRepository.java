package eu.fp7.driver.ost.driver.repository;

import eu.fp7.driver.ost.driver.model.QuestionOption;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface QuestionOptionRepository
        extends PagingAndSortingRepository<QuestionOption, Long>, JpaSpecificationExecutor<QuestionOption> {
    Optional<QuestionOption> findById(Long id);
}
