package eu.fp7.driver.ost.core.security.security.model;

import eu.fp7.driver.ost.core.persistence.db.model.DictionaryObject;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;

@Entity
@GenericGenerator(
        name = "DefaultSeqGen",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {@org.hibernate.annotations.Parameter(name = "sequence_name", value = "auth_permission_seq")}
)
public class AuthPermission extends DictionaryObject
        implements GrantedAuthority {

    @Override
    public String getAuthority() {
        return getShortName();
    }
}
