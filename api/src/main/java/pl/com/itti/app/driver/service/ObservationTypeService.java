package pl.com.itti.app.driver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.driver.dto.ObservationTypeDTO;
import pl.com.itti.app.driver.dto.TrialRoleDTO;
import pl.com.itti.app.driver.model.Answer;
import pl.com.itti.app.driver.model.ObservationType;
import pl.com.itti.app.driver.model.TrialRole;
import pl.com.itti.app.driver.model.TrialSession;
import pl.com.itti.app.driver.repository.ObservationTypeRepository;
import pl.com.itti.app.driver.repository.TrialRoleRepository;
import pl.com.itti.app.driver.repository.TrialSessionRepository;
import pl.com.itti.app.driver.repository.specification.ObservationTypeSpecification;
import pl.com.itti.app.driver.repository.specification.TrialRoleSpecification;
import pl.com.itti.app.driver.util.RepositoryUtils;
import pl.com.itti.app.core.dto.Dto;
import pl.com.itti.app.core.exception.EntityNotFoundException;
import pl.com.itti.app.core.security.security.model.AuthUser;
import pl.com.itti.app.core.security.security.repository.AuthUserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ObservationTypeService {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private ObservationTypeRepository observationTypeRepository;

    @Autowired
    private TrialSessionRepository trialSessionRepository;

    @Autowired
    private TrialRoleRepository trialRoleRepository;

    @Transactional(readOnly = true)
    public List<ObservationType> find(Long trialSessionId) {
        AuthUser authUser = authUserRepository.findOneCurrentlyAuthenticated()
                .orElseThrow(() -> new IllegalArgumentException("Session for current user is closed"));

        TrialSession trialSession = trialSessionRepository.findById(trialSessionId)
                .orElseThrow(() -> new EntityNotFoundException(TrialSession.class, trialSessionId));

        List<ObservationType> observationTypes = observationTypeRepository.findAll(
                getObservationTypeSpecifications(
                        authUser,
                        trialSession
                ));

        observationTypes.sort(Comparator.comparing(observationType -> observationType.getPosition()));

        return observationTypes.stream()
                .filter(observationType -> observationType.isMultiplicity() || hasObservationTypeNoAnswer(observationType, authUser))
                .collect(Collectors.toList());
    }

    private boolean hasObservationTypeNoAnswer(ObservationType observationType, AuthUser authUser) {
        return observationType.getAnswers().stream()
                .noneMatch(answer -> answerContainAuthUserAndIsNotDeleted(answer, authUser));

    }

    boolean answerContainAuthUserAndIsNotDeleted(Answer answer, AuthUser authUser) {
        return answer.getTrialUser().getAuthUser().equals(authUser) && answer.getDeleteComment() == null;
    }


    @Transactional(readOnly = true)
    public ObservationTypeDTO.SchemaItem generateSchema(Long observationTypeId, Long trialSessionId) {
        ObservationType observationType = observationTypeRepository.findById(observationTypeId)
                .orElseThrow(() -> new EntityNotFoundException(ObservationType.class, observationTypeId));

        ObservationTypeDTO.SchemaItem schemaItem = getSchema(observationType);

        schemaItem.roles = getSchemaItemRoles(observationType, trialSessionId);

        return schemaItem;
    }

    private ObservationTypeDTO.SchemaItem getSchema(ObservationType observationType) {
        return Dto.from(observationType, ObservationTypeDTO.SchemaItem.class);
    }

    private List<TrialRoleDTO.ListItem> getSchemaItemRoles(ObservationType observationType, long trialSessionId) {
        List<TrialRoleDTO.ListItem> trialRoles = new ArrayList<>();

        if (observationType.isWithUsers()) {
            AuthUser authUser = authUserRepository.findOneCurrentlyAuthenticated()
                    .orElseThrow(() -> new IllegalArgumentException("Session for current user is closed"));
            TrialSession trialSession = trialSessionRepository.findById(trialSessionId)
                    .orElseThrow(() -> new EntityNotFoundException(TrialSession.class, trialSessionId));

            List<TrialRole> roles = trialRoleRepository.findAll(getTrialRoleSpecifications(authUser, trialSession, observationType));
            trialRoles = Dto.from(roles, TrialRoleDTO.ListItem.class);
        }

        return trialRoles;
    }

    private Specifications<ObservationType> getObservationTypeSpecifications(AuthUser authUser,
                                                                             TrialSession trialSession) {
        Set<Specification<ObservationType>> conditions = new HashSet<>();
        conditions.add(ObservationTypeSpecification.user(authUser, trialSession));
        //conditions.add(ObservationTypeSpecification.trialSession(trialSession));
        return RepositoryUtils.concatenate(conditions);
    }

    private Specifications<TrialRole> getTrialRoleSpecifications(AuthUser authUser,
                                                                 TrialSession trialSession,
                                                                 ObservationType observationType) {
        Set<Specification<TrialRole>> conditions = new HashSet<>();
        conditions.add(TrialRoleSpecification.findByObserver(authUser, trialSession, observationType));
        conditions.add(TrialRoleSpecification.trialSession(trialSession));
        return RepositoryUtils.concatenate(conditions);
    }
}
