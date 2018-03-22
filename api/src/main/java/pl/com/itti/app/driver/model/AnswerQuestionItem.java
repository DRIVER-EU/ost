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
        parameters = {@Parameter(name = "sequence_name", value = "answer_question_item_seq")}
)
public class AnswerQuestionItem extends PersistentObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "answer_id", nullable = false)
    private Answer answer;

    @Column(nullable = false)
    private String fieldValue;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "answerQuestionItem")
    @Builder.Default
    private List<QuestionItem> questionItems= new ArrayList<>();
}
