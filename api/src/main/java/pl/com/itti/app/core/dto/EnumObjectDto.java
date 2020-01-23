package pl.com.itti.app.core.dto;


import pl.com.itti.app.core.persistence.db.model.EnumObject;

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
