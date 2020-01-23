package eu.fp7.driver.ost.core.security.security.web.dto;

import eu.fp7.driver.ost.core.dto.EntityDto;
import eu.fp7.driver.ost.core.security.security.model.AuthUnit;

public final class AuthUnitDto {

    private AuthUnitDto() {
        throw new AssertionError();
    }

    public static class MinimalItem implements EntityDto<AuthUnit> {

        public long id;

        public String shortName;

        public String longName;

        @Override
        public void toDto(AuthUnit authUnit) {
            this.id = authUnit.getId();
            this.shortName = authUnit.getShortName();
            this.longName = authUnit.getLongName();
        }

    }
}
