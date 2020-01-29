package eu.fp7.driver.ost.core.security.security.repository;

import eu.fp7.driver.ost.core.security.security.model.AuthRole;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface AuthRoleRepository extends CrudRepository<AuthRole, Long> {

    List<AuthRole> findAllByOrderByLongNameAsc();

    Set<AuthRole> findByIdIn(List<Long> ids);
}
