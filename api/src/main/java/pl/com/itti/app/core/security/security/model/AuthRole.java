package pl.com.itti.app.core.security.security.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import pl.com.itti.app.core.persistence.db.model.DictionaryObject;
import pl.com.itti.app.core.persistence.db.model.PersistentObject;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@GenericGenerator(
        name = "DefaultSeqGen",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {@org.hibernate.annotations.Parameter(name = "sequence_name", value = "auth_role_seq")}
)
public class AuthRole extends DictionaryObject
        implements GrantedAuthority {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "auth_role_m2m_permissions",
            joinColumns = @JoinColumn(name = "auth_role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "auth_permission_id", referencedColumnName = "id"))
    private Set<AuthPermission> permissions;

    @Override
    public String getAuthority() {
        return getShortName();
    }

    public Set<AuthPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<AuthPermission> permissions) {
        this.permissions = permissions;
    }
}