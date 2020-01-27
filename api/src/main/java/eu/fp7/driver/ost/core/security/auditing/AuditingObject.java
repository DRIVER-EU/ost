package eu.fp7.driver.ost.core.security.auditing;

import eu.fp7.driver.ost.core.persistence.db.model.PersistentObject;
import eu.fp7.driver.ost.core.security.security.model.AuthUser;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.ZonedDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditingObject extends PersistentObject
        implements Serializable {

    private static final long serialVersionUID = 1L;

    @CreatedBy
    @ManyToOne
    @JoinColumn(nullable = false)
    private AuthUser createdBy;

    @CreatedDate
    @Column(nullable = false)
    private ZonedDateTime createdAt;

    @LastModifiedBy
    @ManyToOne
    private AuthUser modifiedBy;

    @LastModifiedDate
    private ZonedDateTime modifiedAt;

    public AuthUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AuthUser createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public AuthUser getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(AuthUser modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public ZonedDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(ZonedDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
