package pl.com.itti.app.core.security.auditing;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pl.com.itti.app.core.persistence.db.model.PersistentObject;
import pl.com.itti.app.core.security.security.model.AuthUser;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;

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
    private OffsetDateTime createdAt;

    @LastModifiedBy
    @ManyToOne
    private AuthUser modifiedBy;

    @LastModifiedDate
    private OffsetDateTime modifiedAt;

    public AuthUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AuthUser createdBy) {
        this.createdBy = createdBy;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public AuthUser getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(AuthUser modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public OffsetDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(OffsetDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
