package eu.fp7.driver.ost.driver.util.schema;

import lombok.Data;

@Data
public abstract class AttachmentNode {

    private final Long id;
    private final String data;
    private final String type;
}
