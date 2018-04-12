package pl.com.itti.app.driver.service;

import co.perpixel.security.model.AuthUser;
import co.perpixel.security.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.driver.dto.TrialUserDTO;
import pl.com.itti.app.driver.repository.UserRoleSessionRepository;

@Service
@Transactional(readOnly = true)
public class TrialUserService {

    @Autowired
    private UserRoleSessionRepository userRoleSessionRepository;

    @Autowired
    private AuthUserRepository authUserRepository;

    public Page<TrialUserDTO.MinimalItem> findByTrialSessionId(long trialSessionId, Pageable pageable) {
        AuthUser authUser = authUserRepository.findOneCurrentlyAuthenticated()
                .orElseThrow(() ->new IllegalArgumentException("Session for current user is closed"));






        return null;
    }
}
