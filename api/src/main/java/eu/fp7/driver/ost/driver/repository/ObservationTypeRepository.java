package eu.fp7.driver.ost.driver.repository;

import eu.fp7.driver.ost.driver.model.ObservationType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ObservationTypeRepository
        extends PagingAndSortingRepository<ObservationType, Long>, JpaSpecificationExecutor<ObservationType> {
    Optional<ObservationType> findById(Long observationTypeId);
    List<ObservationType> findAllByTrialIdAndTrialStageId(Long trialId, Long trialStageId);
    List<ObservationType> findAllByTrialId(Long trialId);
//    List<ObservationType> findAllByTrialIdAndTrialStageIdAndObservationTypeTrialRolesOrderByPosition(Long trialId, Long trialStageId, ObservationTypeTrialRole observationTypeTrialRole);
    List<ObservationType> findAllByTrialIdAndTrialStageIdOrderByPosition(Long trialId, Long trialStageId);
    List<ObservationType> findAllByTrialIdAndTrialStageIdAndName(Long trialId, Long trialStageId, String observationName);
}
