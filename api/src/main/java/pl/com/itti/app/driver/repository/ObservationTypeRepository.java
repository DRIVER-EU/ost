package pl.com.itti.app.driver.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.com.itti.app.driver.model.ObservationType;

import java.util.List;
import java.util.Optional;

public interface ObservationTypeRepository
        extends PagingAndSortingRepository<ObservationType, Long>, JpaSpecificationExecutor<ObservationType> {
    Optional<ObservationType> findById(Long observationTypeId);

    List<ObservationType> findAllByTrialIdAndTrialStageId(Long trialId, Long trialStageId);

    List<ObservationType> findAllByTrialIdAndTrialStageIdAndObservationTypeTrialRolesOOrderByPosition(Long trialId, Long trialStageId, Long trialRoleId);
}
