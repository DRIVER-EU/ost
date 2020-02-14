package eu.fp7.driver.ost.driver.service;

import eu.fp7.driver.ost.core.exception.EntityNotFoundException;
import eu.fp7.driver.ost.driver.model.*;
import eu.fp7.driver.ost.driver.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ObservationTypeTrialRoleService {

    @Autowired
    private ObservationTypeRepository observationTypeRepository;

    @Autowired
    ObservationTypeRoleRepository observationTypeRoleRepository;


    @Autowired
    private TrialRoleRepository trialRoleRepository;



    @Transactional
    public void delete(long roleId, long observationTypeId) {
        ObservationTypeTrialRoleId observationTypeTrialRoleId = new ObservationTypeTrialRoleId();
        observationTypeTrialRoleId.setObservationTypeId(observationTypeId);
        observationTypeTrialRoleId.setTrialRoleId(roleId);
        ObservationTypeTrialRole observationTypeTrialRole  = observationTypeRoleRepository.findObservationTypeTrialRoleById(observationTypeTrialRoleId)
                 .orElseThrow(() -> new EntityNotFoundException(ObservationTypeTrialRole.class, roleId));
        observationTypeRoleRepository.delete(observationTypeTrialRole);
    }

    @Transactional
    public ObservationTypeTrialRole insert(long roleId, long observationTypeId) {

        TrialRole trialRole = trialRoleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException(TrialRole.class, roleId));
        ObservationType observationType = observationTypeRepository.findById(observationTypeId)
                .orElseThrow(() -> new EntityNotFoundException(TrialStage.class, observationTypeId));
        ObservationTypeTrialRole observationTypeTrialRole = ObservationTypeTrialRole.builder()
                .trialRole(trialRole)
                .observationType(observationType)
                .build();

        return observationTypeRoleRepository.save(observationTypeTrialRole);

    }
}
