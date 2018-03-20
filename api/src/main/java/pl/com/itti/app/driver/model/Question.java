package pl.com.itti.app.driver.model;

import co.perpixel.db.model.PersistentObject;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import pl.com.itti.app.driver.model.enums.AnswerType;

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
        parameters = {@Parameter(name = "sequence_name", value = "question_seq")}
)
public class Question extends PersistentObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "observation_type_id", nullable = false)
    private ObservationType observationType;

    @Column(nullable = false)
    private String description;

    @Column(length = 50, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnswerType answerType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question")
    @Builder.Default
    private List<QuestionItem> questionItems = new ArrayList<>();
}
