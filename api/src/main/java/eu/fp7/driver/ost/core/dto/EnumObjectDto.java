package eu.fp7.driver.ost.core.dto;


import eu.fp7.driver.ost.core.persistence.db.model.EnumObject;

public final class EnumObjectDto {

    private EnumObjectDto() {
        throw new AssertionError();
    }

    public static class Item implements EntityDto<EnumObject> {

        public long id;

        public String name;

        @Override
        public void toDto(EnumObject entity) {
            this.id = entity.getId();
            this.name = entity.getName();
        }
    }
}
