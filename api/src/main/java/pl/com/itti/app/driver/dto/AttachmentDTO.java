package pl.com.itti.app.driver.dto;

import pl.com.itti.app.driver.model.Attachment;
import pl.com.itti.app.driver.model.enums.AttachmentType;
import pl.com.itti.app.core.dto.EntityDto;

public final class AttachmentDTO {

    public static class Item implements EntityDto<Attachment> {

        public long id;
        public String uri;
        public String description;
        public Float longitude;
        public Float latitude;
        public Float altitude;
        public AttachmentType type;

        @Override
        public void toDto(Attachment attachment) {
            this.id = attachment.getId();
            this.description = attachment.getDescription();
            this.longitude = attachment.getLongitude();
            this.latitude = attachment.getLatitude();
            this.altitude = attachment.getAltitude();
            this.uri = attachment.getUri();
            this.type = attachment.getType();
        }
    }

    public static class Coordinates {

        public float longitude;
        public float latitude;
        public float altitude;
    }

    private AttachmentDTO() {
        throw new AssertionError();
    }
}
