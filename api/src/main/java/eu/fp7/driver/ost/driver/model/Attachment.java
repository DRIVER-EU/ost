package eu.fp7.driver.ost.driver.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import eu.fp7.driver.ost.core.persistence.db.model.PersistentObject;
import eu.fp7.driver.ost.driver.model.enums.AttachmentType;
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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@GenericGenerator(
        name = "DefaultSeqGen",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {@Parameter(name = "sequence_name", value = "attachment_seq")}
)
public class Attachment extends PersistentObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "answer_id", nullable = false)
    private Answer answer;

    private String uri;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttachmentType type;

    private String description;

    private Float longitude;

    private Float latitude;

    private Float altitude;
}
