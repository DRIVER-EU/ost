package pl.com.itti.app.core.security.security.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import pl.com.itti.app.core.persistence.db.model.DictionaryObject;
import pl.com.itti.app.core.persistence.db.model.PersistentObject;

import javax.persistence.Entity;

@Data
@Entity
@GenericGenerator(
        name = PersistentObject.SEQUENCE_GENERATOR,
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {@org.hibernate.annotations.Parameter(name = "sequence_name", value = "auth_permission_seq")}
)
public class AuthPermission extends DictionaryObject
        implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @Override
    public String getAuthority() {
        return getShortName();
    }
}
