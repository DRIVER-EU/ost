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
