package eu.fp7.driver.ost.driver.web;

import eu.fp7.driver.ost.driver.model.TrialSession;
import eu.fp7.driver.ost.driver.model.enums.SessionStatus;
import eu.fp7.driver.ost.driver.repository.TrialRepository;
import eu.fp7.driver.ost.driver.repository.TrialSessionRepository;
import eu.fp7.driver.ost.driver.util.TrailDeleteException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@OverrideAutoConfiguration(enabled = false)
@org.springframework.transaction.annotation.Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@ImportAutoConfiguration
public class OstSessionControllerTest {

  @Autowired
  private TrialSessionRepository trialSessionRepository;

  @Autowired
  private TrialRepository trialRepository;

  @Autowired
  private OstSessionController ostSessionController;

  @Test
  public void deleteOstSessionById() throws TrailDeleteException {
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
    ResponseEntity responseEntity = ostSessionController.deleteOstSessionById(trialSessionId);

    //then
    Assert.assertEquals(ResponseEntity.status(HttpStatus.OK).body("trialSession with id="
            + trialSessionId + " successfully deleted"), responseEntity);
  }
}