package pl.com.itti.app.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.itti.app.driver.model.AnswerTrialRole;

@Repository
public interface AnswerTrialRoleRepository extends JpaRepository<AnswerTrialRole, Long> {
}
