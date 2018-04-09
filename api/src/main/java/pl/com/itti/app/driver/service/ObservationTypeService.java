package pl.com.itti.app.driver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pl.com.itti.app.driver.model.ObservationType;
import pl.com.itti.app.driver.model.TrialSession;
import pl.com.itti.app.driver.model.enums.SessionStatus;
import pl.com.itti.app.driver.repository.ObservationTypeRepository;
import pl.com.itti.app.driver.repository.ObservationTypeSpecification;
import pl.com.itti.app.driver.repository.TrialSessionRepository;
import pl.com.itti.app.driver.util.RepositoryUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ObservationTypeService {

    @Autowired
    private ObservationTypeRepository observationTypeRepository;

    @Autowired
    private TrialSessionRepository trialSessionRepository;

    public List<ObservationType> find(Long trialSessionId) {
        TrialSession trialSession =  trialSessionRepository.findOne(1L);
        Set<Specification<ObservationType>> conditions = new HashSet<>();
        conditions.add(ObservationTypeSpecification.trialSession(trialSession));

        return observationTypeRepository.findAll(RepositoryUtils.concatenate(conditions));
    }
}
