//package eu.fp7.driver.ost.core.security.security.web.dto;
//
//import eu.fp7.driver.ost.core.dto.DictionaryObjectDto;
//import eu.fp7.driver.ost.core.dto.Dto;
//import eu.fp7.driver.ost.core.dto.EntityDto;
//import eu.fp7.driver.ost.core.dto.EnumObjectDto;
//import eu.fp7.driver.ost.core.dto.FormDto;
//import eu.fp7.driver.ost.core.persistence.db.model.DictionaryObject;
//import eu.fp7.driver.ost.core.security.security.model.AuthPermission;
//import eu.fp7.driver.ost.core.security.security.model.AuthRole;
//import eu.fp7.driver.ost.core.security.security.model.AuthUser;
//import org.hibernate.validator.constraints.Email;
//
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Pattern;
//import javax.validation.constraints.Size;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//public final class AuthUserDto {
//
//    private AuthUserDto() {
//        throw new AssertionError();
//    }
//
//    public static class MinimalItem implements EntityDto<AuthUser> {
//
//        public long id;
//
//        public String login;
//
//        public String firstName;
//
//        public String lastName;
//
//        @Override
//        public void toDto(AuthUser authUser) {
//            this.id = authUser.getId();
//            this.login = authUser.getLogin();
//            this.firstName = authUser.getFirstName();
//            this.lastName = authUser.getLastName();
//        }
//    }
//
//    public static class ListItem extends MinimalItem {
//
//        public long createdAt;
//
//        public MinimalItem createdBy;
//
//        public List<DictionaryObjectDto.MinimalItem> roles;
//
//        public boolean activated;
//
//        @Override
//        public void toDto(AuthUser authUser) {
//            super.toDto(authUser);
//            this.createdAt = authUser.getCreatedAt().toEpochSecond();
//            this.createdBy = authUser.getCreatedBy() != null
//                    ? Dto.from(authUser.getCreatedBy(), MinimalItem.class)
//                    : null;
//            this.roles = Dto.from(authUser.getRoles().stream()
//                            .map(DictionaryObject.class::cast)
//                            .collect(Collectors.toList()),
//                    DictionaryObjectDto.MinimalItem.class);
//            this.activated = authUser.isActivated();
//        }
//    }
//
//    public static class FullItem extends ListItem {
//
//        public String avatar;
//
//        public String email;
//
//        public String contact;
//
//        public long lastLogin;
//
//        public boolean activated;
//
//        public EnumObjectDto.Item position;
//
//        public long modifiedAt;
//
//        public MinimalItem modifiedBy;
//
//        public AuthUnitDto.MinimalItem unit;
//
//        @Override
//        public void toDto(AuthUser authUser) {
//            super.toDto(authUser);
//            this.avatar = authUser.getAvatar();
//            this.email = authUser.getEmail();
//            this.contact = authUser.getContact();
//            this.lastLogin = authUser.getLastLogin() != null
//                    ? authUser.getLastLogin().getTimeInMillis() / 1000 //epoch
//                    : 0L;
//            this.activated = authUser.isActivated();
//            this.position = authUser.getPosition() != null
//                    ? Dto.from(authUser.getPosition(), EnumObjectDto.Item.class)
//                    : null;
//            this.modifiedAt = authUser.getModifiedAt() != null
//                    ? authUser.getModifiedAt().toEpochSecond()
//                    : 0L;
//            this.modifiedBy = authUser.getModifiedBy() != null
//                    ? Dto.from(authUser.getModifiedBy(), MinimalItem.class)
//                    : null;
//            this.unit = Dto.from(authUser.getUnit(), AuthUnitDto.MinimalItem.class);
//        }
//    }
//
//    public static class LoginResponse implements EntityDto<AuthUser> {
//
//        public String login;
//
//        public String firstName;
//
//        public String lastName;
//
//        public String email;
//
//        public Set<String> roles;
//
//        public Set<String> permissions;
//
//        @Override
//        public void toDto(AuthUser authUser) {
//            this.login = authUser.getLogin();
//            this.firstName = authUser.getFirstName();
//            this.lastName = authUser.getLastName();
//            this.email = authUser.getEmail();
//            this.roles = authUser.getRoles()
//                    .stream()
//                    .map(AuthRole::getAuthority)
//                    .collect(Collectors.toSet());
//            this.permissions = authUser.getRoles()
//                    .stream()
//                    .flatMap(role -> role.getPermissions().stream())
//                    .map(AuthPermission::getAuthority)
//                    .collect(Collectors.toSet());
//        }
//    }
//
//    public static class ValidateLoginResponse {
//
//        public boolean valid;
//
//        public boolean unique;
//
//    }
//
//    public static class UpdateFormItem implements FormDto {
//
//        @NotNull
//        @Size(min = 1, max = 80)
//        public String firstName;
//
//        @NotNull
//        @Size(min = 1, max = 160)
//        public String lastName;
//
//        @NotNull
//        @Size(max = 160)
//        @Email
//        public String email;
//
//        public String contact;
//
//        public boolean activated;
//
//        @Size(min = 1)
//        public List<Long> rolesIds;
//
//        public Long positionId;
//
//    }
//
//    public static class CreateFormItem extends UpdateFormItem {
//
//        @NotNull
//        @Size(min = 3, max = 80)
//        @Pattern(regexp = AuthUser.LOGIN_REGEXP)
//        public String login;
//
//        @NotNull
//        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!\"#$%&'()*+,-./:;<=>?@^_`{|}~\\[\\]]).{8,}$",
//                message = "Password must be minimum 8 characters long and contain " +
//                        "one digit, one lowercase letter, one uppercase letter and one special character.")
//        public String password;
//
//    }
//
//    public static class ChangePasswordFormItem implements FormDto {
//
//        @NotNull
//        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!\"#$%&'()*+,-./:;<=>?@^_`{|}~\\[\\]]).{8,}$",
//                message = "Password must be minimum 8 characters long and contain " +
//                        "one digit, one lowercase letter, one uppercase letter and one special character.")
//        public String password;
//
//    }
//}
