package eu.fp7.driver.ost.driver.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import eu.fp7.driver.ost.core.persistence.db.model.PersistentObject;
import eu.fp7.driver.ost.driver.model.enums.AnswerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
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

@Data
@EqualsAndHashCode(callSuper = true)
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

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnswerType answerType;

    @Column(columnDefinition = "text", nullable = false)
    private String jsonSchema;

    @Column(nullable = false)
    @Builder.Default
    private boolean commented = true;

    @Column(nullable = false)
    private int position;
}
