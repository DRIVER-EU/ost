package pl.com.itti.app.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.itti.app.driver.model.Answer;
import pl.com.itti.app.driver.model.AnswerTrialRole;

import java.util.List;

@Repository
public interface AnswerTrialRoleRepository extends JpaRepository<AnswerTrialRole, Long> {
    List<AnswerTrialRole> findByAnswer(Answer answer);
}
