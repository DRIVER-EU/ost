package eu.fp7.driver.ost.driver.repository;

import eu.fp7.driver.ost.driver.model.Trial;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface TrialRepository
        extends PagingAndSortingRepository<Trial, Long>, JpaSpecificationExecutor<Trial> {
    Optional<Trial> findById(Long id);
    Optional<Trial> findByName(String name);
}
