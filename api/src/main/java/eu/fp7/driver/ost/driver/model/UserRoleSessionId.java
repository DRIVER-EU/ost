package eu.fp7.driver.ost.driver.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Embeddable
public class UserRoleSessionId implements Serializable {
    private long trialUserId;
    private long trialRoleId;
    private long trialSessionId;
}
