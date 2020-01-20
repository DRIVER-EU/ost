package pl.com.itti.app.core.security.security.model;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import pl.com.itti.app.core.security.auditing.AuditingDeletableObject;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Entity
@GenericGenerator(
        name = "DefaultSeqGen",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {@org.hibernate.annotations.Parameter(name = "sequence_name", value = "auth_user_seq")}
)
@AssociationOverrides(
        @AssociationOverride(name = "createdBy", joinColumns = @JoinColumn(nullable = true))
)
@BatchSize(size = 25)
public class AuthUser extends AuditingDeletableObject
        implements Serializable {

    public static final String LOGIN_REGEXP = "^[a-zA-Z0-9][a-zA-Z0-9_.-]*$";
    private static final long serialVersionUID = 1L;
    @NotNull
    @Pattern(regexp = LOGIN_REGEXP)
    @Size(min = 3, max = 80)
    @Column(unique = true, length = 80)
    private String login;

    @NotNull
    @Size(min = 60, max = 60)
    @Column(length = 60)
    private String password;

    @NotNull
    @Size(min = 1, max = 80)
    @Column(length = 80)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 160)
    @Column(length = 160)
    private String lastName;

    @Column(length = 160)
    private String avatar;

    @Email
    @Size(max = 160)
    @Column(unique = true, length = 160)
    private String email;

    @Column(columnDefinition = "text")
    private String contact;

    // TODO Change to OffsetDateTime
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar lastLogin;

    @NotNull
    private boolean activated = false;

    @ManyToOne(fetch = FetchType.EAGER)
    private AuthUserPosition position;

    @ManyToOne(fetch = FetchType.LAZY)
    private AuthUnit unit;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "auth_user_m2m_roles",
            joinColumns = @JoinColumn(name = "auth_user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "auth_role_id", referencedColumnName = "id"))
    private Set<AuthRole> roles = new HashSet<>();

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Calendar getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Calendar lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public AuthUserPosition getPosition() {
        return position;
    }

    public void setPosition(AuthUserPosition position) {
        this.position = position;
    }

    public AuthUnit getUnit() {
        return unit;
    }

    public void setUnit(AuthUnit unit) {
        this.unit = unit;
    }

    public Set<AuthRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<AuthRole> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AuthUser authUser = (AuthUser) o;

        if (activated != authUser.activated) return false;
        if (login != null ? !login.equals(authUser.login) : authUser.login != null) return false;
        if (password != null ? !password.equals(authUser.password) : authUser.password != null) return false;
        if (firstName != null ? !firstName.equals(authUser.firstName) : authUser.firstName != null) return false;
        if (lastName != null ? !lastName.equals(authUser.lastName) : authUser.lastName != null) return false;
        if (avatar != null ? !avatar.equals(authUser.avatar) : authUser.avatar != null) return false;
        if (email != null ? !email.equals(authUser.email) : authUser.email != null) return false;
        if (contact != null ? !contact.equals(authUser.contact) : authUser.contact != null) return false;
        if (lastLogin != null ? !lastLogin.equals(authUser.lastLogin) : authUser.lastLogin != null) return false;
        if (position != null ? !position.equals(authUser.position) : authUser.position != null) return false;
        if (unit != null ? !unit.equals(authUser.unit) : authUser.unit != null) return false;
        return roles != null ? roles.equals(authUser.roles) : authUser.roles == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AuthUser{" +
                "login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", lastLogin=" + lastLogin +
                ", activated=" + activated +
                ", roles=" + roles +
                "} " + super.toString();
    }
}