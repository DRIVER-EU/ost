package pl.com.itti.app.core.security.auditing;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@MappedSuperclass
public abstract class AuditingDeletableObject extends AuditingObject
        implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "meta_deleted", nullable = false)
    private boolean deleted = false;

}
