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
import pl.com.itti.app.driver.model.TrialSession;
import pl.com.itti.app.driver.model.enums.SessionStatus;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@OverrideAutoConfiguration(enabled = false)
@org.springframework.transaction.annotation.Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@ImportAutoConfiguration
public class TrialSessionTests {

    @Autowired
    private TrialSessionRepository trialSessionRepository;

    @Autowired
    private TrialRepository trialRepository;

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void findOneById() {
        // when
        TrialSession trialSession = trialSessionRepository.findOne(1L);

        // then
        Assert.assertEquals(1L, trialSession.getId().longValue());
    }

    @Test
    public void findAll() {
        // when
        Page<TrialSession> trialSessionPage = trialSessionRepository.findAll(new PageRequest(1, 10));

        // then
        Assert.assertEquals(4L, trialSessionPage.getTotalElements());
    }

    @Test
    public void create() {
        // given
        TrialSession trialSession = TrialSession.builder()
                .trial(trialRepository.findOne(1L))
                .startTime(LocalDateTime.now())
                .status(SessionStatus.ACTIVE)
                .pausedTime(LocalDateTime.now())
                .build();

        // when
        TrialSession savedTrialSession = trialSessionRepository.save(trialSession);
        Page<TrialSession> trialSessionPage = trialSessionRepository.findAll(new PageRequest(1, 10));

        // then
        Assert.assertEquals(5L, trialSessionPage.getTotalElements());
        Assert.assertEquals(trialSession.getTrial(), savedTrialSession.getTrial());
        Assert.assertEquals(trialSession.getStartTime(), savedTrialSession.getStartTime());
        Assert.assertEquals(trialSession.getStatus(), savedTrialSession.getStatus());
        Assert.assertEquals(trialSession.getPausedTime(), savedTrialSession.getPausedTime());
    }

    @Test
    public void update() {
        // given
        TrialSession trialSession = trialSessionRepository.findOne(1L);
        trialSession.setStatus(SessionStatus.ENDED);

        // when
        TrialSession savedTrialSession = trialSessionRepository.save(trialSession);

        // then
        Assert.assertEquals(SessionStatus.ENDED, savedTrialSession.getStatus());
        Assert.assertEquals(trialSession.getTrial(), savedTrialSession.getTrial());
        Assert.assertEquals(trialSession.getStartTime(), savedTrialSession.getStartTime());
        Assert.assertEquals(trialSession.getPausedTime(), savedTrialSession.getPausedTime());
    }

    @Test
    public void delete() {
        // when
        eventRepository.delete(5L);
        trialSessionRepository.delete(4L);
        Page<TrialSession> trialSessionPage = trialSessionRepository.findAll(new PageRequest(1, 10));

        // then
        Assert.assertEquals(3L, trialSessionPage.getTotalElements());
    }
}
