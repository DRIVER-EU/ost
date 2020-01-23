package eu.fp7.driver.ost.core.security.security.service;

import eu.fp7.driver.ost.core.security.security.model.AuthUserPosition;
import eu.fp7.driver.ost.core.security.security.repository.AuthUserPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthUserPositionService {

    @Autowired
    private AuthUserPositionRepository repository;

    @Transactional(readOnly = true)
    public List<AuthUserPosition> findAll() {
        return repository.findAllByOrderByPositionAsc();
    }
}
