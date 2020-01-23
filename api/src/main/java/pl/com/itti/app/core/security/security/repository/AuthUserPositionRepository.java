package pl.com.itti.app.core.security.security.repository;
import org.springframework.data.repository.CrudRepository;
import pl.com.itti.app.core.security.security.model.AuthUserPosition;

import java.util.List;

public interface AuthUserPositionRepository extends CrudRepository<AuthUserPosition, Long> {

    List<AuthUserPosition> findAllByOrderByPositionAsc();
}
