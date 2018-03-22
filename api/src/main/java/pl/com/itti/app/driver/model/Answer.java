package pl.com.itti.app.driver.model;

import co.perpixel.db.model.PersistentObject;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
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
    @JoinColumn(nullable = false)
    private TrialUser trialUser;

    @Column(nullable = false)
    private LocalDateTime sentRealTime;

    @Column(nullable = false)
    private LocalDateTime simulationTime;

    @Column(nullable = false)
    private String fieldValue;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "answer")
    @Builder.Default
    private List<Attachment> attachments = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "answer")
    @Builder.Default
    private AnswerQuestionItem answerQuestionItem = new AnswerQuestionItem();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "answer")
    @Builder.Default
    private List<TrialRole> trialRoles = new ArrayList<>();
}
