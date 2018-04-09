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
