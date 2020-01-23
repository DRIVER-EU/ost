package eu.fp7.driver.ost.driver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserRoleSession {

    @EmbeddedId
    @Builder.Default
    private UserRoleSessionId id = new UserRoleSessionId();

    @ManyToOne
    @JoinColumn(nullable = false)
    @MapsId("trialUserId")
    private TrialUser trialUser;

    @ManyToOne
    @JoinColumn(nullable = false)
    @MapsId("trialRoleId")
    private TrialRole trialRole;

    @ManyToOne
    @JoinColumn(nullable = false)
    @MapsId("trialSessionId")
    private TrialSession trialSession;
}
