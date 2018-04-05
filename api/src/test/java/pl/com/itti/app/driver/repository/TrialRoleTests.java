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
import pl.com.itti.app.driver.model.TrialRole;
import pl.com.itti.app.driver.model.enums.RoleType;

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
        // when
        TrialRole trialRole = TrialRole.builder()
                .roleType(RoleType.OBSERVER)
                .trial(trialRepository.findOne(1L))
                .name("name")
                .build();
        trialRoleRepository.save(trialRole);
        Page<TrialRole> trialRolePage = trialRoleRepository.findAll(new PageRequest(1,10));

        // then
        Assert.assertEquals(7L, trialRolePage.getTotalElements());
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

    @Test
    public void update() {
        // when
        TrialRole trialRole = trialRoleRepository.findOne(1L);
        trialRole.setRoleType(RoleType.PARTICIPANT);
        trialRoleRepository.save(trialRole);

        // then
        Assert.assertEquals(RoleType.PARTICIPANT, trialRoleRepository.findOne(1L).getRoleType());
    }
}
