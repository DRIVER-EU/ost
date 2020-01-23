package pl.com.itti.app.core.security.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.core.security.security.model.AuthRole;
import pl.com.itti.app.core.security.security.repository.AuthRoleRepository;

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
