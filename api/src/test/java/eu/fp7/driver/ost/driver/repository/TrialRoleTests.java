package eu.fp7.driver.ost.driver.repository;

import eu.fp7.driver.ost.driver.model.TrialRole;
import eu.fp7.driver.ost.driver.model.enums.RoleType;
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
public class TrialRoleTests {

    @Autowired
    private TrialRoleRepository trialRoleRepository;

    @Autowired
    private TrialRepository trialRepository;

    @Autowired
    private UserRoleSessionRepository userRoleSessionRepository;

    @Test
    public void findOneById() {
        // when
        TrialRole trialRole = trialRoleRepository.findOne(1L);

        // then
        Assert.assertEquals(1L, trialRole.getId().longValue());
    }

    @Test
    public void findAll() {
        // when
        Page<TrialRole> trialRolePage = trialRoleRepository.findAll(new PageRequest(1,10));

        // then
        Assert.assertEquals(6L, trialRolePage.getTotalElements());
    }

    @Test
    public void create() {
        // given
        TrialRole trialRole = TrialRole.builder()
                .roleType(RoleType.OBSERVER)
                .trial(trialRepository.findOne(1L))
                .name("name")
                .build();

        // when
        TrialRole savedTrialRole = trialRoleRepository.save(trialRole);
        Page<TrialRole> trialRolePage = trialRoleRepository.findAll(new PageRequest(1,10));

        // then
        Assert.assertEquals(7L, trialRolePage.getTotalElements());
        Assert.assertEquals(trialRole.getRoleType(), savedTrialRole.getRoleType());
        Assert.assertEquals(trialRole.getTrial(), savedTrialRole.getTrial());
        Assert.assertEquals(trialRole.getName(), savedTrialRole.getName());
    }

    @Test
    public void update() {
        // given
        TrialRole trialRole = trialRoleRepository.findOne(1L);
        trialRole.setRoleType(RoleType.PARTICIPANT);

        // when
        TrialRole savedTrialRole = trialRoleRepository.save(trialRole);

        // then
        Assert.assertEquals(RoleType.PARTICIPANT, savedTrialRole.getRoleType());
        Assert.assertEquals(trialRole.getName(), savedTrialRole.getName());
        Assert.assertEquals(trialRole.getTrial(), savedTrialRole.getTrial());
    }

    @Test
    public void delete() {
        // when
        userRoleSessionRepository.deleteAll();
        trialRoleRepository.delete(4L);
        Page<TrialRole> trialRolePage = trialRoleRepository.findAll(new PageRequest(1,10));

        // then
        Assert.assertEquals(5L, trialRolePage.getTotalElements());
    }
}
