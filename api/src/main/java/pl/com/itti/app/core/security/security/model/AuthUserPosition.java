package pl.com.itti.app.core.security.security.model;

import org.hibernate.annotations.GenericGenerator;
import pl.com.itti.app.core.persistence.db.model.EnumObject;

import javax.persistence.Entity;

@Entity
@GenericGenerator(
        name = "DefaultSeqGen",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {@org.hibernate.annotations.Parameter(name = "sequence_name", value = "auth_user_position_seq")}
)
public class AuthUserPosition extends EnumObject {
}

