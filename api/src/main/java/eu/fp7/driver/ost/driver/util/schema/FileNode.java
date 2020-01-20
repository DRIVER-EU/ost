package eu.fp7.driver.ost.driver.util.schema;

import eu.fp7.driver.ost.driver.model.Attachment;

class FileNode extends AttachmentNode {

    FileNode(Attachment attachment) {
        super(attachment.getId(), attachment.getDescription(), attachment.getType().name());
    }
}
