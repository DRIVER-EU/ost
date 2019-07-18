package pl.com.itti.app.core.security.security.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import pl.com.itti.app.core.persistence.db.model.EnumObject;
import pl.com.itti.app.core.persistence.db.model.PersistentObject;

import javax.persistence.Entity;

@Data
@Entity
@GenericGenerator(
        name = PersistentObject.SEQUENCE_GENERATOR,
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {@org.hibernate.annotations.Parameter(name = "sequence_name", value = "auth_user_position_seq")}
)
public class AuthUserPosition extends EnumObject {

    private static final long serialVersionUID = 1L;

}
