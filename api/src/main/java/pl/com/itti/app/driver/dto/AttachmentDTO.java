package pl.com.itti.app.driver.dto;

import co.perpixel.dto.EntityDTO;
import pl.com.itti.app.driver.model.Attachment;
import pl.com.itti.app.driver.model.enums.AttachmentType;

public final class AttachmentDTO {

    public static class Item implements EntityDTO<Attachment> {

        public long id;
        public String uri;
        public AttachmentType type;

        @Override
        public void toDto(Attachment attachment) {
            this.id = attachment.getId();
            this.uri = attachment.getUri();
            this.type = attachment.getType();
        }
    }

    private AttachmentDTO() {
        throw new AssertionError();
    }
}
