package pl.com.itti.app.core.persistence.db.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@EqualsAndHashCode
@ToString
@MappedSuperclass
public abstract class PersistentObject
        implements Serializable {

    public static final String SEQUENCE_GENERATOR = "DefaultSeqGen";

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_GENERATOR)
    private Long id;

    @Column(name = "meta_uuid", length = 36, nullable = false, updatable = false, unique = true, columnDefinition = "varchar(36) default ''")
    private String uuid = UUID.randomUUID().toString();

    @Version
    @Column(name = "meta_lock", columnDefinition = "bigint NOT NULL DEFAULT 0", nullable = false)
    private long lock = 0L;

}
