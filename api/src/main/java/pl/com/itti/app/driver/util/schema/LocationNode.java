package pl.com.itti.app.driver.util.schema;

import pl.com.itti.app.driver.model.Attachment;

class LocationNode extends AttachmentNode {

    private static final String FORMAT_COORDINATES = "[%s, %s, %s]";

    LocationNode(Attachment attachment) {
        super(attachment.getId(), getStringCoordinates(attachment), attachment.getType().name());
    }

    private static String getStringCoordinates(Attachment attachment) {
        return String.format(FORMAT_COORDINATES, attachment.getLatitude(), attachment.getLongitude(), attachment.getAltitude());
    }
}
