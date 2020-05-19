package eu.fp7.driver.ost.driver.service;

import eu.fp7.driver.ost.driver.dto.KeycloakUserDto;
import eu.fp7.driver.ost.driver.util.InvalidDataException;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private RealmResource realmResource;

    private TrialUserService trialUserService;

    public AuthService(RealmResource realmResource, TrialUserService trialUserService) {
        this.realmResource = realmResource;
        this.trialUserService = trialUserService;
    }

    public List<UserRepresentation> findAll(Pageable pageable) {
        return realmResource.users().list(pageable.getOffset(), pageable.getPageSize());
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
            form.roles.forEach(role -> assignRole(userId, role));
            trialUserService.create(userId);
            userRepresentation.setId(userId);
            return userRepresentation;
        } else {
            throw new InvalidDataException("Users login or email already exists");
        }
    }

    private void assignRole(String userId, String role) {
        realmResource.users().get(userId).roles().realmLevel().add(Collections.singletonList(realmResource.roles().get(role).toRepresentation()));
    }

    private UserRepresentation createUserRepresentation(KeycloakUserDto.CreateFormItem form) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(form.login);
        userRepresentation.setFirstName(form.firstName);
        userRepresentation.setLastName(form.lastName);
        userRepresentation.setEmail(form.email);
        userRepresentation.singleAttribute("contact", form.contact);
        userRepresentation.setCredentials(Collections.singletonList(createUserPasswordCredential(form.password)));
        userRepresentation.setRealmRoles(form.roles);
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
}
