package pl.com.itti.app.driver.service;

import co.perpixel.security.model.AuthUser;
import co.perpixel.security.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.driver.dto.TrialDTO;
import pl.com.itti.app.driver.dto.TrialSessionDTO;
import pl.com.itti.app.driver.model.Trial;
import pl.com.itti.app.driver.model.TrialSession;
import pl.com.itti.app.driver.model.UserRoleSession;
import pl.com.itti.app.driver.model.enums.SessionStatus;
import pl.com.itti.app.driver.repository.TrialSessionRepository;
import pl.com.itti.app.driver.repository.UserRoleSessionRepository;
import pl.com.itti.app.driver.repository.UserRoleSessionSpecification;
import pl.com.itti.app.driver.util.RepositoryUtils;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class TrialSessionService {

    @Autowired
    private TrialSessionRepository trialSessionRepository;

    @Autowired
    private UserRoleSessionRepository userRoleSessionRepository;

    @Autowired
    private AuthUserRepository authUserRepository;

    public Page<TrialSessionDTO.FormItem> findByStatus(SessionStatus sessionStatus, Pageable pageable) {
        List<TrialSession> activeTrialSessionList = trialSessionRepository.findByStatus(sessionStatus, pageable);
        List<TrialSessionDTO.FormItem> activeTrialSessionDtoList = activeTrialSessionList.stream()
                    .map(TrialSessionDTO.FormItem::new)
                    .collect(Collectors.toList());

        AuthUser authUser = authUserRepository.findOne(1L);
        Set<Specification<UserRoleSession>> conditions = new HashSet<>();
        conditions.add(UserRoleSessionSpecification.trailSessionStatus(sessionStatus));

        Page<UserRoleSession> userRoleSessions = userRoleSessionRepository.findAll(RepositoryUtils.concatenate(conditions), pageable);

        return new PageImpl<>(activeTrialSessionDtoList, pageable, activeTrialSessionDtoList.size());
    }


//    public Page<TrialDTO.MinimalItem> findTrialWithActiveSessionByTrialSessionManagerId(long trialSessionManagerId, Pageable pageable) {
//            List<TrialSession> trialSessionList = trialSessionRepository.findByTrialSessionManagerId(trialSessionManagerId);
//
//            List<TrialSession> activeTrialSessionList = trialSessionList.stream()
//                    .filter(t -> SessionStatus.STARTED.equals(t.getStatus()))
//                    .collect(Collectors.toList());
//
//            List<Trial> trialList = activeTrialSessionList.stream()
//                    .map(TrialSession::getTrial)
//                    .collect(Collectors.toList());
//
//            List<TrialDTO.MinimalItem> trialDtoList = trialList.stream()
//                    .map(TrialDTO.MinimalItem::new)
//                    .collect(Collectors.toList());
//
//        return new PageImpl<>(trialDtoList, pageable, trialDtoList.size());
//    }


}
