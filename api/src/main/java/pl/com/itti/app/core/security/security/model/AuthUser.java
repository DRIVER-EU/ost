package pl.com.itti.app.core.security.security.model;

import lombok.Data;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import pl.com.itti.app.core.persistence.db.model.PersistentObject;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@GenericGenerator(
        name = PersistentObject.SEQUENCE_GENERATOR,
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {@org.hibernate.annotations.Parameter(name = "sequence_name", value = "auth_user_seq")}
)
@AssociationOverrides(
        @AssociationOverride(name = "createdBy", joinColumns = @JoinColumn(name = "meta_created_by_id", nullable = true))
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

    private ZonedDateTime lastLogin;

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

}

