package eu.fp7.driver.ost.driver.dto;

import com.fasterxml.jackson.databind.JsonNode;
import eu.fp7.driver.ost.core.dto.EntityDto;
import eu.fp7.driver.ost.core.persistence.db.model.PersistentObject;
import eu.fp7.driver.ost.driver.model.ObservationType;
import eu.fp7.driver.ost.driver.util.InternalServerException;
import eu.fp7.driver.ost.driver.util.schema.SchemaCreator;

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
