package pl.com.itti.app.core.security.security.repository;

import org.springframework.data.repository.CrudRepository;
import pl.com.itti.app.core.security.security.model.AuthRole;

import java.util.List;
import java.util.Set;

public interface AuthRoleRepository extends CrudRepository<AuthRole, Long> {

    List<AuthRole> findAllByOrderByLongNameAsc();

    Set<AuthRole> findByIdIn(List<Long> ids);
}
