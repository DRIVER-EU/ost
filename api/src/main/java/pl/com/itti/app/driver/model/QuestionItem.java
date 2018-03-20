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
import pl.com.itti.app.driver.model.enums.Languages;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@GenericGenerator(
        name = "DefaultSeqGen",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {@Parameter(name = "sequence_name", value = "question_item_seq")}
)
public class QuestionItem extends PersistentObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "answer_item_id", nullable = false)
    private AnswerItem answerItem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Languages language;

    @Column(length = 50, nullable = false)
    private String name;
}
