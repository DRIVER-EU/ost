package eu.fp7.driver.ost.driver.model;

import eu.fp7.driver.ost.core.persistence.db.model.PersistentObject;
import eu.fp7.driver.ost.driver.model.enums.Languages;
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
        parameters = {@Parameter(name = "sequence_name", value = "trial_user_seq")}
)
public class TrialUser extends PersistentObject implements Serializable {

    @OneToOne(fetch = FetchType.LAZY)
    private AuthUser authUser;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Languages userLanguage;

    @Column(nullable = false)
    private Boolean isTrialCreator;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trialUser")
    @Builder.Default
    private List<Answer> answers = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trialUser")
    @Builder.Default
    private List<TrialSessionManager> trialSessionManagers = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trialUser")
    @Builder.Default
    private List<TrialManager> trialManagers = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trialUser")
    @Builder.Default
    private List<UserRoleSession> userRoleSessions = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trialUser")
    @Builder.Default
    private List<Event> events = new ArrayList<>();
}
