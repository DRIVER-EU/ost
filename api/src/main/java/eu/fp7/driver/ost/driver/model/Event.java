package eu.fp7.driver.ost.driver.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import eu.fp7.driver.ost.core.persistence.db.model.PersistentObject;
import eu.fp7.driver.ost.driver.model.enums.Languages;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@GenericGenerator(
        name = "DefaultSeqGen",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {@Parameter(name = "sequence_name", value = "event_seq")}
)
public class Event extends PersistentObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "trial_session_id", nullable = false)
    private TrialSession trialSession;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "trial_user_id")
    private TrialUser trialUser;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "trial_role_id")
    private TrialRole trialRole;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int idEvent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Languages languageVersion;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime eventTime;
}
