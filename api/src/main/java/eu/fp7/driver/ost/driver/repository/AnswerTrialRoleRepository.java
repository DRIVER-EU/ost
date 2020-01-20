package eu.fp7.driver.ost.driver.repository;

import eu.fp7.driver.ost.driver.model.Answer;
import eu.fp7.driver.ost.driver.model.AnswerTrialRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerTrialRoleRepository extends JpaRepository<AnswerTrialRole, Long> {
    List<AnswerTrialRole> findByAnswer(Answer answer);
}
