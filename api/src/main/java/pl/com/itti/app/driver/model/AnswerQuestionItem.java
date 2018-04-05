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
public class AnswerQuestionItem {

    @EmbeddedId
    @Builder.Default
    private AnswerQuestionItemId id = new AnswerQuestionItemId();

    @ManyToOne
    @JoinColumn(nullable = false)
    @MapsId("answerId")
    private Answer answer;

    @ManyToOne
    @JoinColumn(nullable = false)
    @MapsId("questionItemId")
    private QuestionItem questionItem;

    private String fieldValue;
}
