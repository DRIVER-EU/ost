package pl.com.itti.app.driver.service;

import co.perpixel.dto.DTO;
import co.perpixel.exception.EntityNotFoundException;
import co.perpixel.security.model.AuthUser;
import co.perpixel.security.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.driver.dto.ObservationTypeCriteriaDTO;
import pl.com.itti.app.driver.dto.ObservationTypeDTO;
import pl.com.itti.app.driver.dto.TrialRoleDTO;
import pl.com.itti.app.driver.model.*;
import pl.com.itti.app.driver.repository.ObservationTypeRepository;
import pl.com.itti.app.driver.repository.ObservationTypeRoleRepository;
import pl.com.itti.app.driver.repository.TrialRoleRepository;
import pl.com.itti.app.driver.repository.TrialSessionRepository;
import pl.com.itti.app.driver.repository.specification.ObservationTypeSpecification;
import pl.com.itti.app.driver.repository.specification.TrialRoleSpecification;
import pl.com.itti.app.driver.util.RepositoryUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class ObservationTypeService {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private ObservationTypeRepository observationTypeRepository;

    @Autowired
    ObservationTypeRoleRepository observationTypeRoleRepository;

    @Autowired
    private TrialSessionRepository trialSessionRepository;

    @Autowired
    private TrialRoleRepository trialRoleRepository;

    @Transactional(readOnly = true)
    public List<ObservationTypeDTO.SchemaItem> generateSchemaList(ObservationTypeCriteriaDTO observationTypeCriteriaDTO) {
//        AuthUser authUser = authUserRepository.findOneCurrentlyAuthenticated()
//                .orElseThrow(() -> new IllegalArgumentException("Session for current user is closed"));
//
//        TrialSession trialSession = trialSessionRepository.findById(observationTypeCriteriaDTO.getTrialSessionId())
//                .orElseThrow(() -> new EntityNotFoundException(TrialSession.class, observationTypeCriteriaDTO.getTrialSessionId()));


        List<ObservationType> observationTypesList = observationTypeRepository
                .findAllByTrialIdAndTrialStageIdOrderByPosition(
                        observationTypeCriteriaDTO.getTrialId(), observationTypeCriteriaDTO.getTrialStageId());

//TODO JKW clean code - stream
//         observationTypes.stream()
//                .filter(observationType -> observationType.isMultiplicity() || hasObservationTypeNoAnswer(observationType, authUser))
//                .collect(Collectors.toList());
//        Optional<TrialRole> role = trialRoleRepository.findById(observationTypeCriteriaDTO.getTrialRoleId());
//        List<ObservationType> list = observationTypesList.stream()
//                .filter(x -> x.getObservationTypeTrialRoles().contains(ObservationTypeTrialRole.builder().observationType(x).trialRole(role.get()).build()))
//                .collect(Collectors.toList());
//        list.contains(null);

        List<ObservationTypeDTO.SchemaItem> generatedSchemaList = new ArrayList<>();
        for (ObservationType observationType : observationTypesList) {

                for( ObservationTypeTrialRole observationTypeTrialRoles:observationType.getObservationTypeTrialRoles())
                {
                    if(observationTypeTrialRoles.getTrialRole()!= null && observationTypeCriteriaDTO.getTrialRoleId().equals(observationTypeTrialRoles.getTrialRole().getId()))
                    {
                        ObservationTypeDTO.SchemaItem schemaItem = getSchema(observationType);

                        schemaItem.roles = getSchemaItemRoles(observationType, observationTypeCriteriaDTO.getTrialSessionId());
                        generatedSchemaList.add(schemaItem);
                    }
                }
        }
        return generatedSchemaList;
    }

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
        return DTO.from(observationType, ObservationTypeDTO.SchemaItem.class);
    }

    private List<TrialRoleDTO.ListItem> getSchemaItemRoles(ObservationType observationType, long trialSessionId) {
        List<TrialRoleDTO.ListItem> trialRoles = new ArrayList<>();

        if (observationType.isWithUsers()) {
            AuthUser authUser = authUserRepository.findOneCurrentlyAuthenticated()
                    .orElseThrow(() -> new IllegalArgumentException("Session for current user is closed"));
            TrialSession trialSession = trialSessionRepository.findById(trialSessionId)
                    .orElseThrow(() -> new EntityNotFoundException(TrialSession.class, trialSessionId));

            List<TrialRole> roles = trialRoleRepository.findAll(getTrialRoleSpecifications(authUser, trialSession, observationType));
            trialRoles = DTO.from(roles, TrialRoleDTO.ListItem.class);
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
