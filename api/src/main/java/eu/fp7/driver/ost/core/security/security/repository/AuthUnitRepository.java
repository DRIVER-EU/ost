//package eu.fp7.driver.ost.core.security.security.repository;
//
//import eu.fp7.driver.ost.core.annotation.IsAuthenticated;
//import eu.fp7.driver.ost.core.security.security.model.AuthUnit;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.Optional;
//
//public interface AuthUnitRepository extends JpaRepository<AuthUnit, Long> {
//
//    @IsAuthenticated
//    @Query("select au from AuthUnit au where au.id = ?#{principal?.unitId}")
//    Optional<AuthUnit> findOneCurrentlyAuthenticated();
//
//    Optional<AuthUnit> findOneByShortName(String shortName);
//}
