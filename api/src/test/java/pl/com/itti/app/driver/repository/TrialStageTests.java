package pl.com.itti.app.driver.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.itti.app.driver.model.TrialStage;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@OverrideAutoConfiguration(enabled = false)
@org.springframework.transaction.annotation.Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@ImportAutoConfiguration
public class TrialStageTests {

    @Autowired
    private TrialStageRepository trialStageRepository;

    @Autowired
    private TrialRepository trialRepository;

    @Autowired
    private TrialSessionRepository trialSessionRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRoleSessionRepository userRoleSessionRepository;

    @Autowired
    private ObservationTypeRepository observationTypeRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    public void findOneById() {
        // when
        TrialStage trialStage = trialStageRepository.findOne(1L);

        // then
        Assert.assertEquals(1L, trialStage.getId().longValue());
    }

    @Test
    public void findAll() {
        // when
        Page<TrialStage> trialStagePage = trialStageRepository.findAll(new PageRequest(1,10));

        // then
        Assert.assertEquals(2L, trialStagePage.getTotalElements());
    }

    @Test
    public void create() {
        // when
        TrialStage trialStage = TrialStage.builder()
                .simulationTime(LocalDateTime.now())
                .name("name")
                .trial(trialRepository.findOne(1L))
                .build();
        trialStageRepository.save(trialStage);
        Page<TrialStage> trialStagePage = trialStageRepository.findAll(new PageRequest(1,10));

        // then
        Assert.assertEquals(3L, trialStagePage.getTotalElements());
    }

    @Test
    public void delete() {
        // when
        eventRepository.deleteAll();
        userRoleSessionRepository.deleteAll();
        questionRepository.deleteAll();
        observationTypeRepository.delete(3L);
        trialSessionRepository.delete(3L);
        trialSessionRepository.delete(4L);
        trialStageRepository.delete(2L);
        Page<TrialStage> trialStagePage = trialStageRepository.findAll(new PageRequest(1,10));

        // then
        Assert.assertEquals(1L, trialStagePage.getTotalElements());
    }

    @Test
    public void update() {
        // when
        TrialStage trialStage = trialStageRepository.findOne(1L);
        trialStage.setName("name");
        trialStageRepository.save(trialStage);

        // then
        Assert.assertEquals("name", trialStageRepository.findOne(1L).getName());
    }
}
