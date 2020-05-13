package eu.fp7.driver.ost.driver.service;

import eu.fp7.driver.ost.core.dto.Dto;
import eu.fp7.driver.ost.core.exception.EntityNotFoundException;
import eu.fp7.driver.ost.driver.model.*;
import eu.fp7.driver.ost.driver.repository.AuthUserRepository;
import eu.fp7.driver.ost.driver.dto.AdminObservationTypeDTO;
import eu.fp7.driver.ost.driver.dto.ObservationTypeCriteriaDTO;
import eu.fp7.driver.ost.driver.dto.ObservationTypeDTO;
import eu.fp7.driver.ost.driver.dto.TrialRoleDTO;
import eu.fp7.driver.ost.driver.repository.ObservationTypeRepository;
import eu.fp7.driver.ost.driver.repository.ObservationTypeRoleRepository;
import eu.fp7.driver.ost.driver.repository.TrialRepository;
import eu.fp7.driver.ost.driver.repository.TrialRoleRepository;
import eu.fp7.driver.ost.driver.repository.TrialSessionRepository;
import eu.fp7.driver.ost.driver.repository.TrialStageRepository;
import eu.fp7.driver.ost.driver.repository.specification.ObservationTypeSpecification;
import eu.fp7.driver.ost.driver.repository.specification.TrialRoleSpecification;
import eu.fp7.driver.ost.driver.util.InvalidDataException;
import eu.fp7.driver.ost.driver.util.RepositoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ObservationTypeService {

    @Autowired
    private TrialUserService trialUserService;

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
        String keycloakUserId = trialUserService.getCurrentKeycloakUserId();

        TrialSession trialSession = trialSessionRepository.findById(trialSessionId)
                .orElseThrow(() -> new EntityNotFoundException(TrialSession.class, trialSessionId));

        List<ObservationType> observationTypes = observationTypeRepository.findAll(
                getObservationTypeSpecifications(
                        keycloakUserId,
                        trialSession
                ));

        observationTypes.sort(Comparator.comparing(observationType -> observationType.getPosition()));

        return observationTypes.stream()
                .filter(observationType -> observationType.isMultiplicity() || hasObservationTypeNoAnswer(observationType, keycloakUserId))
                .collect(Collectors.toList());
    }



    @Transactional(readOnly = true)
    public List<ObservationType> getAllObservationTypesForTrial(long id) {

        List<ObservationType> observationTypes = observationTypeRepository.findAllByTrialId(id);

        return observationTypes.stream()
                .collect(Collectors.toList());
    }

    private boolean hasObservationTypeNoAnswer(ObservationType observationType, String keycloakUserId) {
        return observationType.getAnswers().stream()
                .noneMatch(answer -> answerContainAuthUserAndIsNotDeleted(answer, keycloakUserId));

    }

    boolean answerContainAuthUserAndIsNotDeleted(Answer answer, String keycloakUserId) {
        return answer.getTrialUser().getKeycloakUserId().equals(keycloakUserId) && answer.getDeleteComment() == null;
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
            String keycloakUserId = trialUserService.getCurrentKeycloakUserId();
            TrialSession trialSession = trialSessionRepository.findById(trialSessionId)
                    .orElseThrow(() -> new EntityNotFoundException(TrialSession.class, trialSessionId));

            List<TrialRole> roles = trialRoleRepository.findAll(getTrialRoleSpecifications(keycloakUserId, trialSession, observationType));
            trialRoles = Dto.from(roles, TrialRoleDTO.ListItem.class);
        }

        return trialRoles;
    }

    private Specifications<ObservationType> getObservationTypeSpecifications(String keycloakUserId,
                                                                             TrialSession trialSession) {
        Set<Specification<ObservationType>> conditions = new HashSet<>();
        conditions.add(ObservationTypeSpecification.user(keycloakUserId, trialSession));
        //conditions.add(ObservationTypeSpecification.trialSession(trialSession));
        return RepositoryUtils.concatenate(conditions);
    }

    private Specifications<TrialRole> getTrialRoleSpecifications(String keycloakUserId,
                                                                 TrialSession trialSession,
                                                                 ObservationType observationType) {
        Set<Specification<TrialRole>> conditions = new HashSet<>();
        conditions.add(TrialRoleSpecification.findByObserver(keycloakUserId, trialSession, observationType));
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
        TrialStage trialStage = trialStageRepository.findById(adminObservationTypeDTO.getTrailStageId())
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
