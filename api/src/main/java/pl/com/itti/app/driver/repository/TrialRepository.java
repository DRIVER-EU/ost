package pl.com.itti.app.driver.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.com.itti.app.driver.model.Trial;

import java.util.Optional;

public interface TrialRepository
        extends PagingAndSortingRepository<Trial, Long>, JpaSpecificationExecutor<Trial> {
    Optional<Trial> findById(Long id);
    Optional<Trial> findByName(String name);
}
