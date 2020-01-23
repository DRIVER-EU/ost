package eu.fp7.driver.ost.core.security.auditing;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class AuditingDeletableObject extends AuditingObject
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean deleted = false;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
