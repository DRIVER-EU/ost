package eu.fp7.driver.ost.driver.service;

import eu.fp7.driver.ost.driver.dto.KeycloakUserDto;
import eu.fp7.driver.ost.driver.util.InvalidDataException;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RoleMappingResource;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.admin.client.resource.RoleScopeResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private RealmResource realmResource;

    private TrialUserService trialUserService;

    public final static String CONTACT = "contact";
    public final static String ROLE_ADMIN = "ost_admin";
    public final static String ROLE_OBSERVER = "ost_observer";

    public AuthService(RealmResource realmResource, TrialUserService trialUserService) {
        this.realmResource = realmResource;
        this.trialUserService = trialUserService;
    }

    public Page<UserRepresentation> findAll(Pageable pageable) {
        List<UserRepresentation> userRepresentations = realmResource.users().list(pageable.getOffset(), pageable.getPageSize());
        Integer total = realmResource.users().count();
        return new PageImpl(userRepresentations, pageable,total);
    }

    public List<UserRepresentation> findAllActive() {
        return realmResource.users().list().stream()
                .filter(UserRepresentation::isEnabled)
                .collect(Collectors.toList());
    }

    public UserRepresentation findOne(String id) {
        List<String> roles = getUserRoles(id);
        UserRepresentation userRepresentation = realmResource.users().get(id).toRepresentation();
        userRepresentation.setRealmRoles(roles);
        return userRepresentation;
    }

    private List<String> getUserRoles(String id) {
        return realmResource.users().get(id).roles().getAll().getRealmMappings().stream()
                .map(RoleRepresentation::getName)
                .collect(Collectors.toList());
    }

    public UserRepresentation create(KeycloakUserDto.CreateFormItem form) {
        UserRepresentation userRepresentation = createUserRepresentation(form);
        Response response = realmResource.users().create(userRepresentation);
        if (HttpStatus.CREATED.value() == response.getStatus()) {
            String userId = response.getLocation().getPath().substring(response.getLocation().getPath().lastIndexOf("/") + 1);
            updateRole(userId, form.isAdmin);
            trialUserService.create(userRepresentation.getUsername());
            userRepresentation.setId(userId);
            userRepresentation.setRealmRoles(Collections.singletonList(form.isAdmin ? ROLE_ADMIN : ROLE_OBSERVER));
            return userRepresentation;
        } else {
            throw new InvalidDataException("Users login or email already exists");
        }
    }

    private void updateRole(String userId, boolean isAdmin) {
        RoleScopeResource roleScopeResource = realmResource.users().get(userId).roles().realmLevel();
        RoleRepresentation adminRole = realmResource.roles().get(ROLE_ADMIN).toRepresentation();
        RoleRepresentation observerRole = realmResource.roles().get(ROLE_OBSERVER).toRepresentation();
        if (isAdmin) {
            roleScopeResource.add(Collections.singletonList(adminRole));
            roleScopeResource.remove(Collections.singletonList(observerRole));
        } else {
            roleScopeResource.add(Collections.singletonList(observerRole));
            roleScopeResource.remove(Collections.singletonList(adminRole));
        }
    }

    private UserRepresentation createUserRepresentation(KeycloakUserDto.CreateFormItem form) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(form.login.toLowerCase());
        userRepresentation.setFirstName(form.firstName);
        userRepresentation.setLastName(form.lastName);
        userRepresentation.setEmail(form.email);
        userRepresentation.singleAttribute(CONTACT, form.contact);
        userRepresentation.setCredentials(Collections.singletonList(createUserPasswordCredential(form.password)));
        userRepresentation.setEnabled(form.activated);
        return userRepresentation;
    }



    private CredentialRepresentation createUserPasswordCredential(String password) {
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(password);
        return passwordCred;
    }

    public void delete(String id) {
        UserRepresentation userRepresentation = realmResource.users().get(id).toRepresentation();
        userRepresentation.setEnabled(false);
        realmResource.users().get(id).update(userRepresentation);
    }

    public void changePassword(String id, String password) {
        realmResource.users().get(id).resetPassword(createUserPasswordCredential(password));
    }

    public UserRepresentation update(String id, KeycloakUserDto.UpdateFormItem form) {
        UserRepresentation userRepresentation = realmResource.users().get(id).toRepresentation();
        userRepresentation.setFirstName(form.firstName);
        userRepresentation.setLastName(form.lastName);
        userRepresentation.setEmail(form.email);
        userRepresentation.singleAttribute(CONTACT, form.contact);
        userRepresentation.setEnabled(form.activated);
        updateRole(id, form.isAdmin);
        realmResource.users().get(id).update(userRepresentation);
        userRepresentation.setRealmRoles(Collections.singletonList(form.isAdmin ? ROLE_ADMIN : ROLE_OBSERVER));
        return userRepresentation;
    }
}
