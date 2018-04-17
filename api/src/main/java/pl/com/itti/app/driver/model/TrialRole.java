package pl.com.itti.app.driver.model;

import co.perpixel.db.model.PersistentObject;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import pl.com.itti.app.driver.model.enums.RoleType;

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
        parameters = {@Parameter(name = "sequence_name", value = "trial_role_seq")}
)
public class TrialRole extends PersistentObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "trial_id", nullable = false)
    private Trial trial;

    @Column(length = 50, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roleType;

    @ManyToMany(
            cascade={CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity=pl.com.itti.app.driver.model.TrialRole.class
    )
    @JoinTable(
            name="trial_role_m2m",
            joinColumns=@JoinColumn(name="trial_observer_id"),
            inverseJoinColumns=@JoinColumn(name="trial_participant_id")
    )
    private List<TrialRole> trialRoles;

    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity = pl.com.itti.app.driver.model.TrialRole.class
    )
    @JoinTable(
            name = "trial_role_m2m",
            joinColumns = @JoinColumn(name = "trial_participant_id"),
            inverseJoinColumns = @JoinColumn(name = "trial_observer_id")
    )
    private List<TrialRole> trialRolesParents;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trialRole")
    @Builder.Default
    private List<ObservationTypeTrialRole> observationTypeTrialRoles = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trialRole")
    @Builder.Default
    private List<AnswerTrialRole> answerTrialRoles = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trialRole")
    @Builder.Default
    private List<UserRoleSession> userRoleSessions = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trialRole")
    @Builder.Default
    private List<Event> events = new ArrayList<>();
}