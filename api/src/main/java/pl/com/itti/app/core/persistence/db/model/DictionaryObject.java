package pl.com.itti.app.core.persistence.db.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@MappedSuperclass
public abstract class DictionaryObject extends PersistentObject
        implements Serializable {

    private static final long serialVersionUID = 2L;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(unique = true, length = 50)
    private String shortName;

    @NotNull
    @Size(min = 3, max = 160)
    @Column(length = 160)
    private String longName;

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "bigint NOT NULL DEFAULT 0", nullable = false)
    private long position = 0L;

}
