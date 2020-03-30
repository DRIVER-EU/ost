//package eu.fp7.driver.ost.core.security.security.model;
//
//import eu.fp7.driver.ost.core.security.auditing.AuditingDeletableObject;
//import org.hibernate.annotations.GenericGenerator;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//import java.io.Serializable;
//
//@Entity
//@GenericGenerator(
//        name = "DefaultSeqGen",
//        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
//        parameters = {@org.hibernate.annotations.Parameter(name = "sequence_name", value = "auth_unit_seq")}
//)
//public class AuthUnit extends AuditingDeletableObject
//        implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @NotNull
//    @Size(min = 1, max = 50)
//    @Column(unique = true, length = 50)
//    private String shortName;
//
//    @NotNull
//    @Size(min = 1, max = 160)
//    @Column(length = 160)
//    private String longName;
//
//    public String getShortName() {
//        return shortName;
//    }
//
//    public void setShortName(String shortName) {
//        this.shortName = shortName;
//    }
//
//    public String getLongName() {
//        return longName;
//    }
//
//    public void setLongName(String longName) {
//        this.longName = longName;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;
//
//        AuthUnit authUnit = (AuthUnit) o;
//
//        if (shortName != null ? !shortName.equals(authUnit.shortName) : authUnit.shortName != null) return false;
//        return longName != null ? longName.equals(authUnit.longName) : authUnit.longName == null;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = super.hashCode();
//        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
//        result = 31 * result + (longName != null ? longName.hashCode() : 0);
//        return result;
//    }
//
//    @Override
//    public String toString() {
//        return "AuthUnit{" +
//                "shortName='" + shortName + '\'' +
//                ", longName='" + longName + '\'' +
//                "} " + super.toString();
//    }
//}
//
