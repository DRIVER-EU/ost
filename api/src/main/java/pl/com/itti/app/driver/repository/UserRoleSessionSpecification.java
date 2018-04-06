package pl.com.itti.app.driver.repository;

import org.springframework.data.jpa.domain.Specification;
import pl.com.itti.app.driver.model.UserRoleSession;
import pl.com.itti.app.driver.model.enums.SessionStatus;

public class UserRoleSessionSpecification {

    public static Specification<UserRoleSession> trailSessionStatus(SessionStatus sessionStatus) {
        return null;
    }

}
