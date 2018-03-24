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
public class TrialCreator {

    @EmbeddedId
    private TrialCreatorId id = new TrialCreatorId();

    @ManyToOne
    @JoinColumn(nullable = false)
    @MapsId("trialUserId")
    private TrialUser trialUser;

    @ManyToOne
    @JoinColumn(nullable = false)
    @MapsId("trialId")
    private Trial trial;
}
