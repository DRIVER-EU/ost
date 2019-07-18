package pl.com.itti.app.driver.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import pl.com.itti.app.core.persistence.db.model.PersistentObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
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
    @JoinColumn(name = "trial_stage_id")
    private TrialStage trialStage;

    @Column(nullable = false)
    private String description;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    @Builder.Default
    private boolean multiplicity = true;

    @Column(nullable = false)
    @Builder.Default
    private boolean withUsers = true;

    @Column(nullable = false)
    private int position;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "observationType")
    @Builder.Default
    private List<Question> questions = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "observationType")
    @Builder.Default
    private List<ObservationTypeTrialRole> observationTypeTrialRoles = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "observationType")
    @Builder.Default
    private List<Answer> answers = new ArrayList<>();
}
