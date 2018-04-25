package pl.com.itti.app.driver.model;

import co.perpixel.db.model.PersistentObject;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@GenericGenerator(
        name = "DefaultSeqGen",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {@Parameter(name = "sequence_name", value = "answer_seq")}
)
public class Answer extends PersistentObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "trial_session_id", nullable = false)
    private TrialSession trialSession;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trial_user_id", nullable = false)
    private TrialUser trialUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "observation_type_id", nullable = false)
    private ObservationType observationType;

    @Column(nullable = false)
    private ZonedDateTime simulationTime;

    @Column(nullable = false)
    private LocalDateTime sentSimulationTime;

    @Column(nullable = false)
    private String fieldValue;

    @Column(columnDefinition = "text", nullable = false)
    private String formData;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "answer")
    @Builder.Default
    private List<Attachment> attachments = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "answer")
    @Builder.Default
    private List<AnswerTrialRole> answerTrialRoles = new ArrayList<>();
}
