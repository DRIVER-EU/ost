package pl.com.itti.app.driver.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.itti.app.driver.model.TrialSession;
import pl.com.itti.app.driver.model.enums.SessionStatus;
import pl.com.itti.app.driver.repository.TrialRepository;
import pl.com.itti.app.driver.repository.TrialSessionRepository;
import pl.com.itti.app.driver.util.TrailDeleteException;

import java.time.LocalDateTime;
import java.util.Optional;

@RunWith(SpringRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@OverrideAutoConfiguration(enabled = false)
@org.springframework.transaction.annotation.Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@ImportAutoConfiguration
public class OstSessionServiceTest {

  @Autowired
  private TrialSessionRepository trialSessionRepository;

  @Autowired
  private TrialRepository trialRepository;

  @Autowired
  private OstSessionService ostSessionService;


  @Test
  public void addTrialSession(){
    //given
    TrialSession trialSession = TrialSession.builder()
            .trial(trialRepository.findOne(1L))
            .startTime(LocalDateTime.now())
            .status(SessionStatus.ACTIVE)
            .pausedTime(LocalDateTime.now())
            .build();

    long trialSessionsAmountBeforeSave = trialSessionRepository.count();

    //when
    TrialSession savedTrialSession = trialSessionRepository.save(trialSession);
    long trialSessionsAmountAfterSave = trialSessionRepository.count();

    //then
    Assert.assertEquals(trialSessionsAmountBeforeSave, trialSessionsAmountAfterSave - 1);
  }

  @Test
  public void deleteTrialSession() throws TrailDeleteException {
    //given
    TrialSession trialSession = TrialSession.builder()
            .trial(trialRepository.findOne(1L))
            .startTime(LocalDateTime.now())
            .status(SessionStatus.ACTIVE)
            .pausedTime(LocalDateTime.now())
            .build();

    long trialSessionsAmountBeforeSave = trialSessionRepository.count();
    TrialSession savedTrialSession = trialSessionRepository.save(trialSession);

    Long trialSessionId = savedTrialSession.getId();

    //when
    ostSessionService.deleteTrialSession(trialSessionId);
    long trialSessionsAmountAfterDelete = trialSessionRepository.count();

    //then
    Assert.assertEquals(trialSessionsAmountBeforeSave, trialSessionsAmountAfterDelete);
  }

  @Test
  public void findTrialSessionAfterDelete() throws TrailDeleteException {
    //given
    TrialSession trialSession = TrialSession.builder()
            .trial(trialRepository.findOne(1L))
            .startTime(LocalDateTime.now())
            .status(SessionStatus.ACTIVE)
            .pausedTime(LocalDateTime.now())
            .build();

    TrialSession savedTrialSession = trialSessionRepository.save(trialSession);
    Long trialSessionId = savedTrialSession.getId();

    //when
    ostSessionService.deleteTrialSession(trialSessionId);
    Optional<TrialSession> afterDeleteTrialSession = trialSessionRepository.findById(trialSessionId);

    //then
    Assert.assertEquals(Optional.empty(), afterDeleteTrialSession);
  }
}