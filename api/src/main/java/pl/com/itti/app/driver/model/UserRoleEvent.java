package pl.com.itti.app.driver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserRoleEvent {

    @EmbeddedId
    @Builder.Default
    private UserRoleEventId id = new UserRoleEventId();

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
    @MapsId("eventId")
    private Event event;
}
