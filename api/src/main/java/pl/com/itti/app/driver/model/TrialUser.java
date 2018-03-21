package pl.com.itti.app.driver.model;

import co.perpixel.security.model.AuthUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import pl.com.itti.app.driver.model.enums.Languages;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
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
public class TrialUser extends AuthUser {

    private Languages userLanguage;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "answer")
    @Builder.Default
    private List<Answer> answers;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trialRole")
    @Builder.Default
    private List<TrialRole> roles;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trialSession")
    @Builder.Default
    private List<TrialSession> sessions;
}
