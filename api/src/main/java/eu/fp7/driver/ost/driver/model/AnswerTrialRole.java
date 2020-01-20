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
public class AnswerTrialRole {

    @EmbeddedId
    @Builder.Default
    private AnswerTrialRoleId id = new AnswerTrialRoleId();

    @ManyToOne
    @JoinColumn(nullable = false)
    @MapsId("answerId")
    private Answer answer;

    @ManyToOne
    @JoinColumn(nullable = false)
    @MapsId("trialRoleId")
    private TrialRole trialRole;
}
