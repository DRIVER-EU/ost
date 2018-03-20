package pl.com.itti.app.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.itti.app.driver.model.Answer;

import java.io.Serializable;

@Repository
public interface ObservationRepository extends JpaRepository<Answer, Serializable> {
}
