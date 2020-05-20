package eu.fp7.driver.ost.driver.dto;

import eu.fp7.driver.ost.core.dto.EntityDto;
import eu.fp7.driver.ost.core.dto.FormDto;
import eu.fp7.driver.ost.driver.model.AuthUser;
import eu.fp7.driver.ost.driver.service.AuthService;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.keycloak.representations.idm.UserRepresentation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public final class KeycloakUserDto {

    public static class MinimalItem implements EntityDto<UserRepresentation> {

        public String id;

        public String login;

        public String firstName;

        public String lastName;

        @Override
        public void toDto(UserRepresentation userRepresentation) {
            this.id = userRepresentation.getId();
            this.login = userRepresentation.getUsername();
            this.firstName = userRepresentation.getFirstName();
            this.lastName = userRepresentation.getLastName();
        }
    }

    public static class ListItem extends MinimalItem {

        public boolean activated;

        @Override
        public void toDto(UserRepresentation userRepresentation) {
            super.toDto(userRepresentation);
            this.activated = userRepresentation.isEnabled();
        }
    }

    public static class FullItem extends ListItem {

        public String email;

        public String contact;

        public boolean activated;

        public Boolean isAdmin;


        @Override
        public void toDto(UserRepresentation userRepresentation) {
            super.toDto(userRepresentation);
            this.email = userRepresentation.getEmail();
            this.activated = userRepresentation.isEnabled();
            this.isAdmin = userRepresentation.getRealmRoles().contains(AuthService.ROLE_ADMIN);
            this.contact = userRepresentation.getAttributes().get(AuthService.CONTACT).get(0);
        }
    }

    public static class UpdateFormItem implements FormDto {

        @NotNull
        @Size(min = 1, max = 80)
        public String firstName;

        @NotNull
        @Size(min = 1, max = 160)
        public String lastName;

        @NotNull
        @Size(max = 160)
        @Email
        public String email;

        @NotNull
        @NotEmpty
        public String contact;

        @NotNull
        public Boolean isAdmin;

        @NotNull
        public Boolean activated;
    }

    public static class CreateFormItem extends UpdateFormItem {

        @NotNull
        @Size(min = 3, max = 80)
        @Pattern(regexp = AuthUser.LOGIN_REGEXP)
        public String login;

        @NotNull
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!\"#$%&'()*+,-./:;<=>?@^_`{|}~\\[\\]]).{8,}$",
                message = "Password must be minimum 8 characters long and contain " +
                        "one digit, one lowercase letter, one uppercase letter and one special character.")
        public String password;

    }

    public static class ChangePasswordFormItem implements FormDto {

        @NotNull
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!\"#$%&'()*+,-./:;<=>?@^_`{|}~\\[\\]]).{8,}$",
                message = "Password must be minimum 8 characters long and contain " +
                        "one digit, one lowercase letter, one uppercase letter and one special character.")
        public String password;

    }
}
