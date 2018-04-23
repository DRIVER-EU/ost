package pl.com.itti.app.driver.dto;

import co.perpixel.dto.EntityDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.com.itti.app.driver.model.ObservationType;
import pl.com.itti.app.driver.util.InternalServerException;
import pl.com.itti.app.driver.util.schema.SchemaCreator;

import java.io.IOException;
import java.util.List;

public final class ObservationTypeDTO {

    public static class MinimalItem implements EntityDTO<ObservationType> {

        public long id;

        @Override
        public void toDto(ObservationType observationType) {
            this.id = observationType.getId();
        }
    }

    public static class ListItem extends ObservationTypeDTO.MinimalItem {

        public String name;
        public String description;

        @Override
        public void toDto(ObservationType observationType) {
            super.toDto(observationType);
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
                this.jsonSchema = SchemaCreator.createSchemaForm(observationType.getQuestions());
            } catch (IOException ioe) {
                throw new InternalServerException("Error in jsonSchema", ioe);
            }
        }
    }

    private ObservationTypeDTO() {
        throw new AssertionError();
    }
}
