package eu.fp7.driver.ost.driver.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import eu.fp7.driver.ost.core.persistence.db.model.PersistentObject;
import eu.fp7.driver.ost.driver.model.enums.Languages;
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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
    private Languages languageVersion;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean isDefined;

    @Column(name = "IS_ARCHIVED", nullable = true, columnDefinition = "boolean default false")
    private Boolean isArchived;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "init_id")
    private ObservationType initId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trial")
    @Builder.Default
    private List<TrialStage> trialStages = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trial")
    @Builder.Default
    private List<ObservationType> observationTypes = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trial")
    @Builder.Default
    private List<TrialSession> trialSessions = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trial")
    @Builder.Default
    private List<TrialRole> trialRoles = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trial")
    @Builder.Default
    private List<TrialManager> trialManagers = new ArrayList<>();
}
