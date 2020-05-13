package eu.fp7.driver.ost.driver.repository;

import eu.fp7.driver.ost.driver.model.TrialUser;
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

import java.util.UUID;

@RunWith(SpringRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@OverrideAutoConfiguration(enabled = false)
@org.springframework.transaction.annotation.Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@ImportAutoConfiguration
public class TrialUserTests {

    @Autowired
    private TrialUserRepository trialUserRepository;

    @Autowired
    private TrialSessionManagerRepository trialSessionManagerRepository;

    @Autowired
    private UserRoleSessionRepository userRoleSessionRepository;

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void findOneById() {
        // when
        TrialUser trialUser = trialUserRepository.findOne(1L);

        // then
        Assert.assertEquals(1L, trialUser.getId().longValue());
    }

    @Test
    public void findAll() {
        // when
        Page<TrialUser> trialUserPage = trialUserRepository.findAll(new PageRequest(1,10));

        // then
        Assert.assertEquals(6L, trialUserPage.getTotalElements());
    }

    @Test
    public void create() {
        // given
        String keycloakId = UUID.randomUUID().toString();
        TrialUser trialUser = TrialUser.builder()
                .keycloakUserId(keycloakId)
                .userLanguage(Languages.POLISH)
                .isTrialCreator(Boolean.FALSE)
                .build();

        // when
        TrialUser savedTrialUser = trialUserRepository.save(trialUser);
        Page<TrialUser> trialUserPage = trialUserRepository.findAll(new PageRequest(1,10));

        // then
        Assert.assertEquals(7L, trialUserPage.getTotalElements());
        Assert.assertEquals(trialUser.getKeycloakUserId(), savedTrialUser.getKeycloakUserId());
        Assert.assertEquals(trialUser.getUserLanguage(), savedTrialUser.getUserLanguage());
        Assert.assertEquals(trialUser.getIsTrialCreator(), savedTrialUser.getIsTrialCreator());
    }

    @Test
    public void update() {
        // given
        TrialUser trialUser = trialUserRepository.findOne(2L);
        trialUser.setUserLanguage(Languages.POLISH);

        // when
        TrialUser savedTrialUser = trialUserRepository.save(trialUser);

        // then
        Assert.assertEquals(Languages.POLISH, savedTrialUser.getUserLanguage());
        Assert.assertEquals(trialUser.getKeycloakUserId(), savedTrialUser.getKeycloakUserId());
        Assert.assertEquals(trialUser.getIsTrialCreator(), savedTrialUser.getIsTrialCreator());
    }

    @Test
    public void delete() {
        // when
        trialSessionManagerRepository.deleteAll();
        userRoleSessionRepository.deleteAll();
        eventRepository.delete(3L);
        trialUserRepository.delete(2L);
        Page<TrialUser> trialUserPage = trialUserRepository.findAll(new PageRequest(1,10));

        // then
        Assert.assertEquals(5L, trialUserPage.getTotalElements());
    }
}
