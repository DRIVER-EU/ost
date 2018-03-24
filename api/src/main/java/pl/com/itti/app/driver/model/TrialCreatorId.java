package pl.com.itti.app.driver.model;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Embeddable
public class TrialCreatorId implements Serializable {
    private long trialUserId;
    private long trialId;
}
