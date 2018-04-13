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
public class QuestionAnswer {

    @EmbeddedId
    @Builder.Default
    private QuestionAnswerId id = new QuestionAnswerId();

    @ManyToOne
    @JoinColumn(nullable = false)
    @MapsId("questionId")
    private Question question;

    @ManyToOne
    @JoinColumn(nullable = false)
    @MapsId("answerId")
    private Answer answer;
}
