package eu.fp7.driver.ost.driver.repository;

import eu.fp7.driver.ost.driver.model.Trial;
import eu.fp7.driver.ost.driver.model.enums.Languages;
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

@RunWith(SpringRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@OverrideAutoConfiguration(enabled = false)
@org.springframework.transaction.annotation.Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@ImportAutoConfiguration
public class TrialTests {

    @Autowired
    private TrialRepository trialRepository;

    @Autowired
    private TrialManagerRepository trialManagerRepository;

    @Autowired
    private TrialStageRepository trialStageRepository;

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

    @Autowired
    private TrialRoleRepository trialRoleRepository;

    @Test
    public void findOneById() {
        // when
        Trial trial = trialRepository.findOne(1L);

        // then
        Assert.assertEquals(1L, trial.getId().longValue());
    }

    @Test
    public void findAll() {
        // when
        Page<Trial> trialPage = trialRepository.findAll(new PageRequest(1, 10));

        // then
        Assert.assertEquals(2L, trialPage.getTotalElements());
    }

    @Test
    public void create() {
        // given
        Trial trial = Trial.builder()
                .languageVersion(Languages.ENGLISH)
                .name("test_name")
                .description("test_description")
                .build();

        // when
        Trial savedTrial = trialRepository.save(trial);
        Page<Trial> trialPage = trialRepository.findAll(new PageRequest(1, 10));

        // then
        Assert.assertEquals(3L, trialPage.getTotalElements());
        Assert.assertEquals(trial.getLanguageVersion(), savedTrial.getLanguageVersion());
        Assert.assertEquals(trial.getName(), savedTrial.getName());
        Assert.assertEquals(trial.getDescription(), savedTrial.getDescription());
    }

    @Test
    public void update() {
        // given
        Trial trial = trialRepository.findOne(1L);
        trial.setLanguageVersion(Languages.POLISH);

        // when
        Trial savedTrial = trialRepository.save(trial);

        // then
        Assert.assertEquals(Languages.POLISH, trialRepository.findOne(1L).getLanguageVersion());
        Assert.assertEquals(trial.getName(), savedTrial.getName());
        Assert.assertEquals(trial.getDescription(), savedTrial.getDescription());
    }

    @Test
    public void delete() {
        // when
        userRoleSessionRepository.deleteAll();
        trialRoleRepository.delete(4L);
        trialRoleRepository.delete(5L);
        trialRoleRepository.delete(6L);
        questionRepository.deleteAll();
        observationTypeRepository.delete(3L);
        eventRepository.delete(4L);
        eventRepository.delete(5L);
        eventRepository.delete(6L);
        trialSessionRepository.delete(3L);
        trialSessionRepository.delete(4L);
        trialStageRepository.delete(2L);
        trialRepository.delete(2L);
        Page<Trial> trialPage = trialRepository.findAll(new PageRequest(1, 10));

        // then
        Assert.assertEquals(1L, trialPage.getTotalElements());
    }
}
