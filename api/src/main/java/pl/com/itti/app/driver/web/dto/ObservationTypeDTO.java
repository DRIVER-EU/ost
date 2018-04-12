package pl.com.itti.app.driver.web.dto;

import co.perpixel.dto.EntityDTO;
import com.fasterxml.jackson.databind.JsonNode;
import pl.com.itti.app.driver.model.ObservationType;

import java.util.List;

public class ObservationTypeDTO {

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

    public static class SchemaItem {
        public long date;
        public List<TrialUserDTO.MinimalItem> users;
        public JsonNode schema;

    }
}
