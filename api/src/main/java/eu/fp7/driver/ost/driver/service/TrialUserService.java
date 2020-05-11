package eu.fp7.driver.ost.driver.service;

import eu.fp7.driver.ost.driver.model.AuthUser;
import eu.fp7.driver.ost.driver.repository.AuthUserRepository;
import eu.fp7.driver.ost.driver.model.TrialUser;
import eu.fp7.driver.ost.driver.repository.TrialUserRepository;
import eu.fp7.driver.ost.driver.repository.specification.TrialUserSpecification;
import eu.fp7.driver.ost.driver.util.ForbiddenException;
import eu.fp7.driver.ost.driver.util.RepositoryUtils;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class TrialUserService {

    @Autowired
    private TrialUserRepository trialUserRepository;

    @Autowired
    private AuthUserRepository authUserRepository;

    public Page<TrialUser> findByTrialSessionId(Long trialSessionId, Pageable pageable) {
        AuthUser authUser = getCurrentUser();

        checkIsTrialSessionManager(authUser, trialSessionId);

        Set<Specification<TrialUser>> conditions = new HashSet<>();
        conditions.add(TrialUserSpecification.trialUsers(trialSessionId));
        return trialUserRepository.findAll(RepositoryUtils.concatenate(conditions), pageable);
    }

    public void checkIsTrialSessionManager(AuthUser userToCheck, Long trialSessionId) {
        Set<Specification<TrialUser>> managerConditions = new HashSet<>();
        managerConditions.add(TrialUserSpecification.trialSessionManager(userToCheck, trialSessionId));
        Optional.ofNullable(trialUserRepository.findOne(RepositoryUtils.concatenate(managerConditions)))
                .orElseThrow(() -> new ForbiddenException("Permitted only for TrialSessionManager"));
    }

    public void checkHasTrialSession(AuthUser userToCheck, Long trialSessionId) {
        Set<Specification<TrialUser>> managerConditions = new HashSet<>();
        managerConditions.add(TrialUserSpecification.trialSessionUser(userToCheck, trialSessionId));
        Optional.ofNullable(trialUserRepository.findOne(RepositoryUtils.concatenate(managerConditions)))
                .orElseThrow(() -> new ForbiddenException("Permitted only for TrialSessionUser"));
    }

    public AuthUser getCurrentUser() {
        return  getCurrentKeycloakUser();
    }


    public AuthUser getCurrentKeycloakUser(){
        KeycloakAuthenticationToken keycloakAuthenticationToken = ((KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication());
        KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal) keycloakAuthenticationToken.getPrincipal();
        AccessToken accessToken = keycloakPrincipal.getKeycloakSecurityContext().getToken();
        AccessToken.Access access = accessToken.getRealmAccess();
        String[] roleSet = access.getRoles().toArray(new String[0]);
        String userKeycloakId = accessToken.getPreferredUsername();
        AuthUser currentUser;
        Optional<AuthUser> currentUserOptional = authUserRepository.findOneByLogin(userKeycloakId);
        if(currentUserOptional.isPresent()){
            currentUser = currentUserOptional.get();
        }
        else{
            currentUser = new AuthUser();
            currentUser.setLogin(userKeycloakId);
            TrialUser currentTrialUser = new TrialUser();
            currentTrialUser.setAuthUser(currentUser);
            trialUserRepository.save(currentTrialUser);
        }
        return currentUser;
    }
}
