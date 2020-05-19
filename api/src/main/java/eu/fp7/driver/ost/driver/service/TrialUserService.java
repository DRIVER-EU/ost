package eu.fp7.driver.ost.driver.service;

import eu.fp7.driver.ost.driver.model.TrialUser;
import eu.fp7.driver.ost.driver.model.enums.Languages;
import eu.fp7.driver.ost.driver.repository.TrialUserRepository;
import eu.fp7.driver.ost.driver.repository.specification.TrialUserSpecification;
import eu.fp7.driver.ost.driver.util.ForbiddenException;
import eu.fp7.driver.ost.driver.util.RepositoryUtils;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class TrialUserService {

    @Autowired
    private TrialUserRepository trialUserRepository;

    @Transactional(readOnly = true)
    public Page<TrialUser> findAll(Pageable pageable) {
        return trialUserRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<TrialUser> findByTrialSessionId(Long trialSessionId, Pageable pageable) {
        String keycloakUserId = getCurrentKeycloakUserId();

        checkIsTrialSessionManager(keycloakUserId, trialSessionId);

        Set<Specification<TrialUser>> conditions = new HashSet<>();
        conditions.add(TrialUserSpecification.trialUsers(trialSessionId));
        return trialUserRepository.findAll(RepositoryUtils.concatenate(conditions), pageable);
    }

    @Transactional(readOnly = true)
    public void checkIsTrialSessionManager(String keycloakUserId, Long trialSessionId) {
        Set<Specification<TrialUser>> managerConditions = new HashSet<>();
        managerConditions.add(TrialUserSpecification.trialSessionManager(keycloakUserId, trialSessionId));
        Optional.ofNullable(trialUserRepository.findOne(RepositoryUtils.concatenate(managerConditions)))
                .orElseThrow(() -> new ForbiddenException("Permitted only for TrialSessionManager"));
    }

    @Transactional(readOnly = true)
    public void checkHasTrialSession(String keycloakUserId, Long trialSessionId) {
        Set<Specification<TrialUser>> managerConditions = new HashSet<>();
        managerConditions.add(TrialUserSpecification.trialSessionUser(keycloakUserId, trialSessionId));
        Optional.ofNullable(trialUserRepository.findOne(RepositoryUtils.concatenate(managerConditions)))
                .orElseThrow(() -> new ForbiddenException("Permitted only for TrialSessionUser"));
    }

    @Transactional(readOnly = true)
    public String getCurrentKeycloakUserId() {
        KeycloakAuthenticationToken keycloakAuthenticationToken = ((KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication());
        KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal) keycloakAuthenticationToken.getPrincipal();
        return keycloakPrincipal.getName();
    }

    public TrialUser create(String keycloakId) {
        TrialUser trialUser = TrialUser.builder()
                .keycloakUserId(keycloakId)
                .isTrialCreator(false)
                .userLanguage(Languages.ENGLISH)
                .build();
        return trialUserRepository.save(trialUser);
    }
}
