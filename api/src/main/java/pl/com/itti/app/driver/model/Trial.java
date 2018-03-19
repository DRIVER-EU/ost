package pl.com.itti.app.driver.model;

import co.perpixel.db.model.PersistentObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import pl.com.itti.app.driver.model.enums.Languages;

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
        parameters = {@Parameter(name = "sequence_name", value = "trial_seq")}
)
public class Trial extends PersistentObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Languages language;

    @Column(length = 50, nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trial")
    @Builder.Default
    private List<TrialStage> trialStages = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trial")
    @Builder.Default
    private List<ObservationType> observationTypes = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trial")
    @Builder.Default
    private List<TrialSession> trialSessions = new ArrayList<>();
}
