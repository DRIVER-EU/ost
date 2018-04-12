package pl.com.itti.app.driver.service;

import co.perpixel.exception.EntityNotFoundException;
import co.perpixel.security.model.AuthUser;
import co.perpixel.security.repository.AuthUserRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.driver.model.ObservationType;
import pl.com.itti.app.driver.model.TrialSession;
import pl.com.itti.app.driver.repository.ObservationTypeRepository;
import pl.com.itti.app.driver.repository.ObservationTypeSpecification;
import pl.com.itti.app.driver.repository.TrialSessionRepository;
import pl.com.itti.app.driver.util.RepositoryUtils;
import pl.com.itti.app.driver.util.schema.SchemaCreator;
import pl.com.itti.app.driver.web.dto.ObservationTypeDTO;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ObservationTypeService {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private ObservationTypeRepository observationTypeRepository;

    @Autowired
    private TrialSessionRepository trialSessionRepository;

    @Transactional(readOnly = true)
    public Page<ObservationType> find(Long trialSessionId, Pageable pageable) {
        AuthUser authUser = authUserRepository.findOneCurrentlyAuthenticated()
                .orElseThrow(() -> new IllegalArgumentException("Session for current user is closed"));

        TrialSession trialSession = Optional.ofNullable(trialSessionRepository.findOne(trialSessionId))
                .orElseThrow(() -> new EntityNotFoundException(TrialSession.class, trialSessionId));

        return observationTypeRepository.findAll(
                getObservationTypeSpecifications(
                        authUser,
                        trialSession
                ), pageable
        );
    }

    public ObservationTypeDTO.SchemaItem generateSchema(Long observationTypeId) {
        ObservationType observationType = Optional.ofNullable(observationTypeRepository.findOne(observationTypeId))
                .orElseThrow(() -> new EntityNotFoundException(TrialSession.class, observationTypeId));
        ObjectNode objectNode = SchemaCreator.createSchemaForm(observationType.getQuestions());
        ObservationTypeDTO.SchemaItem schemaItem = new ObservationTypeDTO.SchemaItem();
        schemaItem.date = 12312312L;
        schemaItem.schema = objectNode;
        return schemaItem;
    }

    private Specifications<ObservationType> getObservationTypeSpecifications(AuthUser authUser,
                                                                             TrialSession trialSession) {
        Set<Specification<ObservationType>> conditions = new HashSet<>();
        conditions.add(ObservationTypeSpecification.user(authUser));
        conditions.add(ObservationTypeSpecification.trialSession(trialSession));
        return RepositoryUtils.concatenate(conditions);
    }
}
