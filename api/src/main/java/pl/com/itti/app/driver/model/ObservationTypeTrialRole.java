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
public class ObservationTypeTrialRole {

    @EmbeddedId
    @Builder.Default
    private ObservationTypeTrialRoleId id = new ObservationTypeTrialRoleId();

    @ManyToOne
    @JoinColumn(nullable = false)
    @MapsId("observationTypeId")
    private ObservationType observationType;

    @ManyToOne
    @JoinColumn(nullable = false)
    @MapsId("trialRoleId")
    private TrialRole trialRole;
}
