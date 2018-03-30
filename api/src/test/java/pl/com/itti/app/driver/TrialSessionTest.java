package pl.com.itti.app.driver;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.com.itti.app.driver.model.TrialSession;
import pl.com.itti.app.driver.repository.TrialSessionRepository;

import javax.transaction.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional
public class TrialSessionTest {

    @Autowired
    private TrialSessionRepository trialSessionRepository;

    @Test
    public void findOneByTest() {

        // when
        TrialSession trialSession = trialSessionRepository.findOne(1L);

        // then
        Assert.assertEquals(1L, trialSession.getId().longValue());
    }
}
