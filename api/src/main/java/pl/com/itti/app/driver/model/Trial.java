package pl.com.itti.app.driver.model;

import co.perpixel.db.model.PersistentObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@GenericGenerator(
        name = "DefaultSeqGen",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {@Parameter(name = "sequence_name", value = "trial_seq")}
)
public class Trial extends PersistentObject implements Serializable {

    private static final long serialVersionUID = 1L;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Languages language;

    @Column(nullable = false)
    private String name;
}
