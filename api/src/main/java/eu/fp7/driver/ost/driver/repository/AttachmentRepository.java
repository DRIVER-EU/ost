package eu.fp7.driver.ost.driver.repository;

import eu.fp7.driver.ost.driver.model.Answer;
import eu.fp7.driver.ost.driver.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    Optional<Attachment> findById(long id);
    List<Attachment> findByAnswer(Answer answer);
}
