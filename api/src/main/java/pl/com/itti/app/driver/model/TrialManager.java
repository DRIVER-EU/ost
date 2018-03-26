package pl.com.itti.app.driver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.com.itti.app.driver.model.enums.ManagementRoleType;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TrialManager {

    @EmbeddedId
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
