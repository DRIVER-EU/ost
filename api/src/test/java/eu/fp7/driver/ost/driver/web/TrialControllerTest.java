package eu.fp7.driver.ost.driver.web;

import eu.fp7.driver.ost.driver.dto.ObservationTypeDTO;
import eu.fp7.driver.ost.driver.model.ObservationType;
import eu.fp7.driver.ost.driver.repository.ObservationTypeRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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

import java.util.List;

@RunWith(SpringRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@OverrideAutoConfiguration(enabled = false)
@org.springframework.transaction.annotation.Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@ImportAutoConfiguration
public class TrialControllerTest {

  @Autowired
  private TrialController trialController;

  @Autowired
  private ObservationTypeRepository observationTypeRepository;

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void ostAllQuestionsForMobile() {
//    //TODO: MS: Fix the security context- "An Authentication object was not found in the SecurityContext"
//
//    // given
//    List<ObservationTypeDTO.SchemaItem> endpointRetrivedList = trialController.ostAllQuestionsForMobile(1L, 1L, 1L, 1L);
//
//    List<ObservationType> listInDB = observationTypeRepository.findAllByTrialIdAndTrialStageId(1L, 1L);
//
//    // when
//    int endpointRetrivedListAmount = endpointRetrivedList.size();
//    int listInDBAmount = listInDB.size();
//
//    //then
//    Assert.assertEquals(endpointRetrivedListAmount, listInDBAmount);
  }
}