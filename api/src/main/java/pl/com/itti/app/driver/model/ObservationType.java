package pl.com.itti.app.driver.model;

import co.perpixel.db.model.PersistentObject;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@GenericGenerator(
        name = "DefaultSeqGen",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {@Parameter(name = "sequence_name", value = "observation_type_seq")}
)
public class ObservationType extends PersistentObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "trial_id", nullable = false)
    private Trial trial;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "trial_stage_id", nullable = false)
    private TrialStage trialStage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "trial_role_id", nullable = false)
    private TrialRole trialRole;

    @Column(nullable = false)
    private String description;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean multiplicity;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "observationType")
    @Builder.Default
    private List<Question> questions = new ArrayList<>();
}
