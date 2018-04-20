package pl.com.itti.app.driver.service;

import co.perpixel.dto.DTO;
import co.perpixel.exception.EntityNotFoundException;
import co.perpixel.security.model.AuthUser;
import co.perpixel.security.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.driver.dto.ObservationTypeDTO;
import pl.com.itti.app.driver.dto.TrialUserDTO;
import pl.com.itti.app.driver.model.ObservationType;
import pl.com.itti.app.driver.model.TrialSession;
import pl.com.itti.app.driver.model.TrialUser;
import pl.com.itti.app.driver.repository.ObservationTypeRepository;
import pl.com.itti.app.driver.repository.TrialRoleRepository;
import pl.com.itti.app.driver.repository.TrialSessionRepository;
import pl.com.itti.app.driver.repository.TrialUserRepository;
import pl.com.itti.app.driver.repository.specification.ObservationTypeSpecification;
import pl.com.itti.app.driver.repository.specification.TrialUserSpecification;
import pl.com.itti.app.driver.util.RepositoryUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @Autowired
    private TrialRoleRepository trialRoleRepository;

    @Autowired
    private TrialUserRepository trialUserRepository;

    @Transactional(readOnly = true)
    public Page<ObservationType> find(Long trialSessionId, Pageable pageable) {
        AuthUser authUser = authUserRepository.findOneCurrentlyAuthenticated()
                .orElseThrow(() -> new IllegalArgumentException("Session for current user is closed"));

        TrialSession trialSession = trialSessionRepository.findById(trialSessionId)
                .orElseThrow(() -> new EntityNotFoundException(TrialSession.class, trialSessionId));

        return observationTypeRepository.findAll(
                getObservationTypeSpecifications(
                        authUser,
                        trialSession
                ), pageable
        );
    }

    public ObservationTypeDTO.SchemaItem generateSchema(Long observationTypeId, Long trialSessionId) {
        ObservationType observationType = observationTypeRepository.findById(observationTypeId)
                .orElseThrow(() -> new EntityNotFoundException(TrialSession.class, observationTypeId));

        ObservationTypeDTO.SchemaItem schemaItem = DTO.from(observationType, ObservationTypeDTO.SchemaItem.class);

        schemaItem.users = getSchemaItemUsers(observationType, trialSessionId);

        return schemaItem;
    }

    private List<TrialUserDTO.ListItem> getSchemaItemUsers(ObservationType observationType, long trialSessionId) {
        List<TrialUserDTO.ListItem> trialUsers = new ArrayList<>();
        if (observationType.isWithUsers()) {
            AuthUser authUser = authUserRepository.findOneCurrentlyAuthenticated()
                    .orElseThrow(() -> new IllegalArgumentException("Session for current user is closed"));
            TrialSession trialSession = trialSessionRepository.findById(trialSessionId)
                    .orElseThrow(() -> new EntityNotFoundException(TrialSession.class, trialSessionId));

            List<TrialUser> users = trialUserRepository.findAll(getTrialUserSpecifications(authUser, trialSession, observationType));
            trialUsers = DTO.from(users, TrialUserDTO.ListItem.class);
        }
        return trialUsers;
    }

    private Specifications<ObservationType> getObservationTypeSpecifications(AuthUser authUser,
                                                                             TrialSession trialSession) {
        Set<Specification<ObservationType>> conditions = new HashSet<>();
        conditions.add(ObservationTypeSpecification.user(authUser, trialSession));
        conditions.add(ObservationTypeSpecification.trialSession(trialSession));
        return RepositoryUtils.concatenate(conditions);
    }

    private Specifications<TrialUser> getTrialUserSpecifications(AuthUser authUser,
                                                                 TrialSession trialSession,
                                                                 ObservationType observationType) {
        Set<Specification<TrialUser>> conditions = new HashSet<>();
        conditions.add(TrialUserSpecification.findByObserver(authUser, trialSession, observationType));
        return RepositoryUtils.concatenate(conditions);
    }
}
