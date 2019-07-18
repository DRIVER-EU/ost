package pl.com.itti.app.driver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.core.dto.Dto;
import pl.com.itti.app.core.exception.EntityNotFoundException;
import pl.com.itti.app.core.security.security.model.AuthUser;
import pl.com.itti.app.core.security.security.repository.AuthUserRepository;
import pl.com.itti.app.driver.dto.AdminObservationTypeDTO;
import pl.com.itti.app.driver.dto.ObservationTypeCriteriaDTO;
import pl.com.itti.app.driver.dto.ObservationTypeDTO;
import pl.com.itti.app.driver.dto.TrialRoleDTO;
import pl.com.itti.app.driver.model.Answer;
import pl.com.itti.app.driver.model.ObservationType;
import pl.com.itti.app.driver.model.ObservationTypeTrialRole;
import pl.com.itti.app.driver.model.Trial;
import pl.com.itti.app.driver.model.TrialRole;
import pl.com.itti.app.driver.model.TrialSession;
import pl.com.itti.app.driver.model.TrialStage;
import pl.com.itti.app.driver.repository.ObservationTypeRepository;
import pl.com.itti.app.driver.repository.ObservationTypeRoleRepository;
import pl.com.itti.app.driver.repository.TrialRepository;
import pl.com.itti.app.driver.repository.TrialRoleRepository;
import pl.com.itti.app.driver.repository.TrialSessionRepository;
import pl.com.itti.app.driver.repository.TrialStageRepository;
import pl.com.itti.app.driver.repository.specification.ObservationTypeSpecification;
import pl.com.itti.app.driver.repository.specification.TrialRoleSpecification;
import pl.com.itti.app.driver.util.InvalidDataException;
import pl.com.itti.app.driver.util.RepositoryUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired
    private TrialStageRepository trialStageRepository;

    @Autowired
    private TrialRepository trialRepository;

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


    @Transactional
    public void delete(long id) {
        ObservationType observationType  = observationTypeRepository.findById(id)
                 .orElseThrow(() -> new EntityNotFoundException(ObservationType.class, id));
        observationTypeRepository.delete(observationType);
    }

    @Transactional
    public ObservationType update(AdminObservationTypeDTO.FullItem adminObservationTypeDTO) {

        if (adminObservationTypeDTO.getId() == 0) {
            throw new InvalidDataException("No questionSet Id was given");
        }
        ObservationType observationType = observationTypeRepository.findById(adminObservationTypeDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException(ObservationType.class, adminObservationTypeDTO.getId()));

        observationType.setName(adminObservationTypeDTO.getName());
        observationType.setDescription(adminObservationTypeDTO.getDescription());
        observationType.setPosition(adminObservationTypeDTO.getPosition());

        return observationTypeRepository.save(observationType);
    }

    @Transactional
    public ObservationType insert(AdminObservationTypeDTO.FullItem adminObservationTypeDTO) {
        if (adminObservationTypeDTO.getTrailId() == 0) {
            new InvalidDataException("Data Error missing trial Id type in request ");
        }
        if (adminObservationTypeDTO.getTrailStageId() == 0) {
            new InvalidDataException("Data Error missing trial stage Id type in request ");
        }

        Trial trial = trialRepository.findById(adminObservationTypeDTO.getTrailId())
                .orElseThrow(() -> new EntityNotFoundException(Trial.class, adminObservationTypeDTO.getTrailId()));
        TrialStage trialStage = trialStageRepository.findById(adminObservationTypeDTO.getTrailId())
                .orElseThrow(() -> new EntityNotFoundException(TrialStage.class, adminObservationTypeDTO.getTrailStageId()));


        ObservationType observationType = ObservationType.builder()
                .trial(trial)
                .trialStage(trialStage)
                .name(adminObservationTypeDTO.getName())
                .description(adminObservationTypeDTO.getDescription())
                .multiplicity(adminObservationTypeDTO.isMultiplicity())
                .position(adminObservationTypeDTO.getPosition())
                .build();

        return observationTypeRepository.save(observationType);

    }

    @Transactional
    public ObservationType getFullObservationType(Long id) {
        ObservationType observationType = observationTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(TrialStage.class, id));
        return observationType;
    }
}
