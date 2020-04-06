package eu.fp7.driver.ost.driver.service;

import eu.fp7.driver.ost.core.exception.EntityNotFoundException;
import eu.fp7.driver.ost.driver.model.AuthUser;
import eu.fp7.driver.ost.driver.dto.AdminTrialStageDTO;
import eu.fp7.driver.ost.driver.model.Trial;
import eu.fp7.driver.ost.driver.model.TrialStage;
import eu.fp7.driver.ost.driver.repository.TrialStageRepository;
import eu.fp7.driver.ost.driver.repository.specification.TrialStageSpecification;
import eu.fp7.driver.ost.driver.util.InvalidDataException;
import eu.fp7.driver.ost.driver.util.RepositoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
