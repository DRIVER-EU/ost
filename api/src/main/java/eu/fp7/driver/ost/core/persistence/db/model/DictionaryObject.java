package eu.fp7.driver.ost.core.persistence.db.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@MappedSuperclass
public abstract class DictionaryObject extends PersistentObject
        implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(unique = true, length = 50)
    private String shortName;

    @Size(min = 1, max = 160)
    @Column(length = 160)
    private String longName;

    @Column(columnDefinition = "text")
    private String description;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        DictionaryObject that = (DictionaryObject) o;

        if (shortName != null ? !shortName.equals(that.shortName) : that.shortName != null) return false;
        return longName != null ? longName.equals(that.longName) : that.longName == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        result = 31 * result + (longName != null ? longName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DictionaryObject{" +
                "shortName='" + shortName + '\'' +
                ", longName='" + longName + '\'' +
                "} " + super.toString();
    }
}
