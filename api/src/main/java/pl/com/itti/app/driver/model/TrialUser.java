package pl.com.itti.app.driver.model;

import co.perpixel.db.model.PersistentObject;
import co.perpixel.security.model.AuthUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import pl.com.itti.app.driver.model.enums.Languages;

import javax.persistence.*;
import java.io.Serializable;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trialUser")
    @Builder.Default
    private List<Answer> answers;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trialUser")
    @Builder.Default
    private List<TrialRole> trialRoles;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "trialUsers")
    @Builder.Default
    private List<TrialSession> trialSessions;
}
