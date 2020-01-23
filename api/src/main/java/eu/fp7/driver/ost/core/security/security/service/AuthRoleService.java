package eu.fp7.driver.ost.core.security.security.service;

import eu.fp7.driver.ost.core.security.security.model.AuthRole;
import eu.fp7.driver.ost.core.security.security.repository.AuthRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthRoleService {

    @Autowired
    private AuthRoleRepository repository;

    @Transactional(readOnly = true)
    public List<AuthRole> findAll() {
        return repository.findAllByOrderByLongNameAsc();
    }
}
