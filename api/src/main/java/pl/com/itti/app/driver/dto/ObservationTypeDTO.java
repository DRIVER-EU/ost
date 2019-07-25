package pl.com.itti.app.driver.dto;

import com.fasterxml.jackson.databind.JsonNode;
import pl.com.itti.app.driver.model.ObservationType;
import pl.com.itti.app.driver.util.InternalServerException;
import pl.com.itti.app.driver.util.schema.SchemaCreator;
import pl.com.itti.app.core.dto.EntityDto;
import pl.com.itti.app.core.persistence.db.model.PersistentObject;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public final class ObservationTypeDTO {

    public static class MinimalItem implements EntityDto<ObservationType> {

        public long id;

        @Override
        public void toDto(ObservationType observationType) {
            this.id = observationType.getId();
        }
    }

    public static class ListItem extends MinimalItem {

        public List<Long> answersId;
        public String name;
        public String description;

        @Override
        public void toDto(ObservationType observationType) {
            super.toDto(observationType);
            this.answersId = observationType.getAnswers()
                    .stream()
                    .map(PersistentObject::getId)
                    .collect(Collectors.toList());
            this.name = observationType.getName();
            this.description = observationType.getDescription();
        }
    }

    public static class SchemaItem extends ListItem {

        public List<TrialRoleDTO.ListItem> roles;
        public JsonNode jsonSchema;

        @Override
        public void toDto(ObservationType observationType) {
            super.toDto(observationType);

            try {
                this.jsonSchema = SchemaCreator.createSchemaForm(observationType.getQuestions(), false);
            } catch (IOException ioe) {
                throw new InternalServerException("Error in jsonSchema", ioe);
            }
        }
    }

    private ObservationTypeDTO() {
        throw new AssertionError();
    }
}
