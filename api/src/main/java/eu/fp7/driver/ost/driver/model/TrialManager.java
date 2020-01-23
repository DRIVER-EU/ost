package eu.fp7.driver.ost.driver.model;

import eu.fp7.driver.ost.driver.model.enums.ManagementRoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TrialManager {

    @EmbeddedId
    @Builder.Default
    private TrialManagerId id = new TrialManagerId();

    @ManyToOne
    @JoinColumn(nullable = false)
    @MapsId("trialUserId")
    private TrialUser trialUser;

    @ManyToOne
    @JoinColumn(nullable = false)
    @MapsId("trialId")
    private Trial trial;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ManagementRoleType managementRole;
}
