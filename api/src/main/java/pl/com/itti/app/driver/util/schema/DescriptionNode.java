package pl.com.itti.app.driver.util.schema;

import pl.com.itti.app.driver.model.Attachment;

class DescriptionNode extends AttachmentNode {

    DescriptionNode(Attachment attachment) {
        super(attachment.getId(), attachment.getDescription(), attachment.getType().name());
    }
}
