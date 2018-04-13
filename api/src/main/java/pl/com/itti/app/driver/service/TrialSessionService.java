package pl.com.itti.app.driver.service;

import co.perpixel.security.model.AuthUser;
import co.perpixel.security.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.driver.dto.TrialSessionDTO;
import pl.com.itti.app.driver.dto.TrialUserDTO;
import pl.com.itti.app.driver.model.TrialSession;
import pl.com.itti.app.driver.model.UserRoleSession;
import pl.com.itti.app.driver.model.enums.SessionStatus;
import pl.com.itti.app.driver.repository.TrialSessionRepository;
import pl.com.itti.app.driver.repository.UserRoleSessionRepository;
import pl.com.itti.app.driver.repository.specification.UserRoleSessionSpecification;
import pl.com.itti.app.driver.util.RepositoryUtils;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class TrialSessionService {

    @Autowired
    private TrialSessionRepository trialSessionRepository;

    @Autowired
    private UserRoleSessionRepository userRoleSessionRepository;

    @Autowired
    private AuthUserRepository authUserRepository;

    public Page<TrialSessionDTO.ListItem> findByStatus(SessionStatus sessionStatus, Pageable pageable) {
        AuthUser authUser = authUserRepository.findOneCurrentlyAuthenticated()
                .orElseThrow(() ->new IllegalArgumentException("Session for current user is closed"));

        Set<Specification<UserRoleSession>> conditions = new HashSet<>();
        conditions.add(UserRoleSessionSpecification.trailSessionStatus(sessionStatus));
        conditions.add(UserRoleSessionSpecification.authUser(authUser));

        Page<UserRoleSession> userRoleSessions = userRoleSessionRepository.findAll(RepositoryUtils.concatenate(conditions), pageable);
        return userRoleSessions.map(source -> new TrialSessionDTO.ListItem(source.getTrialSession()));
    }
}
