package pl.com.itti.app.core.security.auditing;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pl.com.itti.app.core.persistence.db.model.PersistentObject;
import pl.com.itti.app.core.security.security.model.AuthUser;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditingObject extends PersistentObject
        implements Serializable {

    private static final long serialVersionUID = 1L;

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "meta_created_by_id", nullable = false)
    private AuthUser createdBy;

    @CreatedDate
    @Column(name = "meta_created_at", nullable = false)
    private ZonedDateTime createdAt;

    @LastModifiedBy
    @ManyToOne
    @JoinColumn(name = "meta_modified_by_id")
    private AuthUser modifiedBy;

    @LastModifiedDate
    @Column(name = "meta_modified_at")
    private ZonedDateTime modifiedAt;

}
