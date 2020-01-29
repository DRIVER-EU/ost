package eu.fp7.driver.ost.driver.model.enums;

import java.util.ArrayList;
import java.util.List;

public enum SessionStatus {
    ACTIVE,
    SUSPENDED,
    ENDED;

    public static List<SessionStatus> getStatuses() {
        ArrayList<SessionStatus> statuses = new ArrayList<>();
        for (SessionStatus status : SessionStatus.values()) {
            statuses.add(status);
        }
        return statuses;
    }

}

