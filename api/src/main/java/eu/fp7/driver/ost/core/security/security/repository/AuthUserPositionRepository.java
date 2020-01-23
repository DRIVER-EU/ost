package eu.fp7.driver.ost.core.security.security.repository;

import eu.fp7.driver.ost.core.security.security.model.AuthUserPosition;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AuthUserPositionRepository extends CrudRepository<AuthUserPosition, Long> {

    List<AuthUserPosition> findAllByOrderByPositionAsc();
}
