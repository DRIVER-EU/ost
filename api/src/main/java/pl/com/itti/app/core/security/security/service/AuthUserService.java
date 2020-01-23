package pl.com.itti.app.core.security.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.core.exception.EntityNotFoundException;
import pl.com.itti.app.core.security.security.core.UserDetailsUtils;
import pl.com.itti.app.core.security.security.model.AuthUnit;
import pl.com.itti.app.core.security.security.model.AuthUser;
import pl.com.itti.app.core.security.security.repository.AuthRoleRepository;
import pl.com.itti.app.core.security.security.repository.AuthUnitRepository;
import pl.com.itti.app.core.security.security.repository.AuthUserPositionRepository;
import pl.com.itti.app.core.security.security.repository.AuthUserRepository;
import pl.com.itti.app.core.security.security.web.dto.AuthUserDto;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthUserService {

    @Autowired
    private AuthUserRepository repository;

    @Autowired
    private AuthUnitRepository authUnitRepository;

    @Autowired
    private AuthUserPositionRepository authUserPositionRepository;

    @Autowired
    private AuthRoleRepository authRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Page<AuthUser> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public AuthUser findOne(long id) {
        AuthUser entity = Optional.ofNullable(repository.findOne(id))
                .orElseThrow(() -> new EntityNotFoundException(AuthUser.class, id));

        // TODO this should be @PostAuthorize / @PreAuthorize, but for create, update & delete it doesn't work :(
        if (!UserDetailsUtils.isCurrentUserInUnit(entity.getUnit().getId())) {
            throw new AccessDeniedException("Access denied");
        }

        return entity;
    }

    @Transactional
    public AuthUser create(AuthUserDto.CreateFormItem form) {
        AuthUser entity = new AuthUser();
        entity.setLogin(form.login);
        entity.setPassword(passwordEncoder.encode(form.password));
        entity.setUnit(authUnitRepository.findOneCurrentlyAuthenticated()
                .orElseThrow(() -> new EntityNotFoundException(AuthUnit.class)));
        return update(entity, form);
    }

    @Transactional
    public AuthUser update(long id, AuthUserDto.UpdateFormItem form) {
        AuthUser entity = findOne(id);
        return update(entity, form);
    }

    @Transactional
    public void changePassword(long id, String password) {
        AuthUser entity = findOne(id);
        entity.setPassword(passwordEncoder.encode(password));
        repository.save(entity);
    }

    @Transactional
    public void delete(long id) {
        AuthUser entity = findOne(id);
        entity.setDeleted(true);
        repository.save(entity);
    }

    /**
     * Checks if login is valid (1st boolean) and unique (2nd boolean)
     *
     * @param login
     * @return Pair of booleans, Pair.of(isValid, isUnique)
     */
    @Transactional(readOnly = true)
    public Pair<Boolean, Boolean> validateLogin(String login) {
        Pattern pattern = Pattern.compile(AuthUser.LOGIN_REGEXP);
        Matcher matcher = pattern.matcher(login);
        return Pair.of(matcher.matches(),
                !repository.findOneByLogin(login).isPresent());
    }

    private AuthUser update(AuthUser entity, AuthUserDto.UpdateFormItem form) {
        entity.setFirstName(form.firstName);
        entity.setLastName(form.lastName);
        entity.setEmail(form.email);
        entity.setContact(form.contact);
        entity.setActivated(form.activated);
        entity.setPosition(Optional.ofNullable(form.positionId)
                .map(id -> authUserPositionRepository.findOne(id))
                .orElse(null));
        entity.setRoles(authRoleRepository.findByIdIn(form.rolesIds));
        return repository.save(entity);
    }
}
