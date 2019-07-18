package pl.com.itti.app.core.security.security.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import pl.com.itti.app.core.persistence.db.model.PersistentObject;
import pl.com.itti.app.core.security.auditing.AuditingDeletableObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@GenericGenerator(
        name = PersistentObject.SEQUENCE_GENERATOR,
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {@org.hibernate.annotations.Parameter(name = "sequence_name", value = "auth_unit_seq")}
)
public class AuthUnit extends AuditingDeletableObject
        implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(unique = true, length = 50)
    private String shortName;

    @NotNull
    @Size(min = 1, max = 160)
    @Column(length = 160)
    private String longName;

}

