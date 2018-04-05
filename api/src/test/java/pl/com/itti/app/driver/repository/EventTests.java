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
import pl.com.itti.app.driver.model.Event;
import pl.com.itti.app.driver.model.enums.Languages;
import pl.com.itti.app.driver.repository.EventRepository;
import pl.com.itti.app.driver.repository.TrialSessionRepository;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@OverrideAutoConfiguration(enabled = false)
@org.springframework.transaction.annotation.Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@ImportAutoConfiguration
public class EventTests {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TrialSessionRepository trialSessionRepository;

    @Test
    public void findOneById() {
        // when
        Event event = eventRepository.findOne(1L);

        // then
        Assert.assertEquals(1L, event.getId().longValue());
    }

    @Test
    public void findAll() {
        // when
        Page<Event> eventPage = eventRepository.findAll(new PageRequest(1, 10));

        // then
        Assert.assertEquals(6L, eventPage.getTotalElements());
    }

    @Test
    public void create() {
        // when
        Event event = Event.builder()
                .trialSession(trialSessionRepository.findOne(1L))
                .idEvent(123)
                .eventTime(LocalDateTime.now())
                .languageVersion(Languages.POLISH)
                .name("name")
                .description("description")
                .build();
        eventRepository.save(event);
        Page<Event> eventPage = eventRepository.findAll(new PageRequest(1, 10));

        // then
        Assert.assertEquals(7L, eventPage.getTotalElements());
    }

    @Test
    public void delete() {
        // when
        eventRepository.delete(1L);
        Page<Event> eventPage = eventRepository.findAll(new PageRequest(1, 10));

        // then
        Assert.assertEquals(5L, eventPage.getTotalElements());
    }

    @Test
    public void update() {
        // when
        Event event = eventRepository.findOne(1L);
        event.setLanguageVersion(Languages.POLISH);
        eventRepository.save(event);

        // then
        Assert.assertEquals(Languages.POLISH, eventRepository.findOne(1L).getLanguageVersion());
    }
}
