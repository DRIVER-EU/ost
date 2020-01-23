package pl.com.itti.app.core.security.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.core.security.security.model.AuthUserPosition;
import pl.com.itti.app.core.security.security.repository.AuthUserPositionRepository;

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
