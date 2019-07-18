package pl.com.itti.app.driver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.core.security.security.model.AuthUser;
import pl.com.itti.app.driver.dto.AdminTrialStageDTO;
import pl.com.itti.app.driver.model.Trial;
import pl.com.itti.app.driver.model.TrialStage;
import pl.com.itti.app.driver.repository.TrialStageRepository;
import pl.com.itti.app.driver.repository.specification.TrialStageSpecification;
import pl.com.itti.app.driver.util.InvalidDataException;
import pl.com.itti.app.driver.util.RepositoryUtils;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class TrialStageService {

    @Autowired
    private TrialStageRepository trialStageRepository;

    @Autowired
    private TrialUserService trialUserService;

    @Autowired
    private TrialService trialService;

    @Transactional(readOnly = true)
    public Page<TrialStage> findByTrialSessionId(Long trialSessionId, Pageable pageable) {
        AuthUser authUser = trialUserService.getCurrentUser();
        trialUserService.checkIsTrialSessionManager(authUser, trialSessionId);
        return trialStageRepository.findAll(getTrialStageSessionSpecifications(trialSessionId), pageable);
    }

    @Transactional(readOnly = true)
    public Page<TrialStage> findByTrialId(Long trialId, Pageable pageable) {
        return trialStageRepository.findAllByTrialId(trialId, pageable);
    }

    @Transactional(readOnly = true)
    public TrialStage findById(Long id) {
        TrialStage trialStage = trialStageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(TrialStage.class, id));
        return trialStage;
    }
    @Transactional
    public void delete(long id) {
        TrialStage trialStage = trialStageRepository.findById(id).orElseThrow(() ->  new EntityNotFoundException(TrialStage.class, id));
        trialStageRepository.delete(trialStage);
    }

    @Transactional
    public TrialStage update(AdminTrialStageDTO.ListItem adminTrialStageDTO) {

        if(adminTrialStageDTO.getId() == 0)
        {
            throw new InvalidDataException("No trial stage Id was given");
        }
        TrialStage trialStage = trialStageRepository.findById(adminTrialStageDTO.getId()).orElseThrow(() -> new EntityNotFoundException(TrialStage.class, adminTrialStageDTO.getId()));
        trialStage.setName(adminTrialStageDTO.getName());
        return  trialStageRepository.save(trialStage);
    }
    @Transactional
    public TrialStage insert(AdminTrialStageDTO.ListItem adminTrialStageDTO) {
        Trial trial;
        try{
             trial = trialService.getTrialById(adminTrialStageDTO.getTrialId());
        }
        catch (Exception e)
        {
          throw   new EntityNotFoundException(Trial.class, adminTrialStageDTO.getTrialId());
        }
        TrialStage trialStage = TrialStage.builder()
                .trial(trial)
                .name(adminTrialStageDTO.getName())
                .simulationTime(LocalDateTime.now())
                .build();
        return  trialStageRepository.save(trialStage);
    }

    @Transactional
    public AdminTrialStageDTO.FullItem getTrailStageWithQuestions(Long id) {
        TrialStage trialStage = trialStageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(TrialStage.class, id));
        AdminTrialStageDTO.FullItem adminTrialStageDTO  = new AdminTrialStageDTO.FullItem();
        adminTrialStageDTO.toDto(trialStage);
        return adminTrialStageDTO;
    }

    private Specification<TrialStage> getTrialStageSessionSpecifications(Long trialSessionId) {
        Set<Specification<TrialStage>> conditions = new HashSet<>();
        conditions.add(TrialStageSpecification.trialStage(trialSessionId));
        return RepositoryUtils.concatenate(conditions);
    }



}
