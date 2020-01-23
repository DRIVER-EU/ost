package pl.com.itti.app.core.security.security.web.dto;

import pl.com.itti.app.core.dto.EntityDto;
import pl.com.itti.app.core.security.security.model.AuthUnit;

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
