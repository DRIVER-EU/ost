package eu.fp7.driver.ost.core.dto;


import eu.fp7.driver.ost.core.persistence.db.model.DictionaryObject;

public final class DictionaryObjectDto {

    private DictionaryObjectDto() {
        throw new AssertionError();
    }

    public static class MinimalItem implements EntityDto<DictionaryObject> {

        public long id;

        public String shortName;

        public String longName;

        @Override
        public void toDto(DictionaryObject entity) {
            this.id = entity.getId();
            this.shortName = entity.getShortName();
            this.longName = entity.getLongName();
        }
    }

    public static class Item extends MinimalItem {

        public String description;

        @Override
        public void toDto(DictionaryObject entity) {
            super.toDto(entity);
            this.description = entity.getDescription();
        }
    }
}
