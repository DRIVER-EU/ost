package pl.com.itti.app.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.itti.app.driver.model.Answer;
import pl.com.itti.app.driver.model.Attachment;

import java.util.List;
import java.util.Optional;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    Optional<Attachment> findById(long id);
    List<Attachment> findByAnswer(Answer answer);
}
