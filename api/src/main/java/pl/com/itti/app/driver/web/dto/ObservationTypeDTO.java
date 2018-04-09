package pl.com.itti.app.driver.web.dto;

import co.perpixel.dto.EntityDTO;
import pl.com.itti.app.driver.model.ObservationType;

public class ObservationTypeDTO {

    public static class Item implements EntityDTO<ObservationType> {
        public String name;
        public String description;

        @Override
        public void toDto(ObservationType observationType) {
            this.name = observationType.getName();
            this.description = observationType.getDescription();
        }
    }
}
