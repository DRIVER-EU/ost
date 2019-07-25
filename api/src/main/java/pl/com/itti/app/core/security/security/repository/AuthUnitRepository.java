package pl.com.itti.app.core.security.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.com.itti.app.core.annotation.IsAuthenticated;
import pl.com.itti.app.core.security.security.model.AuthUnit;

import java.util.Optional;

public interface AuthUnitRepository extends JpaRepository<AuthUnit, Long> {

    @IsAuthenticated
    @Query("select au from AuthUnit au where au.id = ?#{principal?.unitId}")
    Optional<AuthUnit> findOneCurrentlyAuthenticated();

    Optional<AuthUnit> findOneByShortName(String shortName);
}
