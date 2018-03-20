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

    @Column(nullable = false)
    private int sentRealTime;

    @Column(nullable = false)
    private int simulationTime;

    @Column(nullable = false)
    private String value;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "answer")
    @Builder.Default
    private List<Attachment> attachments = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "answer")
    @Builder.Default
    private List<AnswerItem> answerItems= new ArrayList<>();
}
