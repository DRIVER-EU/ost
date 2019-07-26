package pl.com.itti.app.core.persistence.db.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class EnumObject extends PersistentObject
        implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(unique = true, length = 250, nullable = false)
    private String name;

    @Column(nullable = false)
    private long position = 0L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        EnumObject that = (EnumObject) o;

        if (position != that.position) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (int) (position ^ (position >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "EnumObject{" +
                "name='" + name + '\'' +
                ", position=" + position +
                "} " + super.toString();
    }
}