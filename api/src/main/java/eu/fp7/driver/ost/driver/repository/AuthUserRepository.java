package eu.fp7.driver.ost.driver.repository;

import eu.fp7.driver.ost.core.annotation.IsAuthenticated;
import eu.fp7.driver.ost.driver.model.AuthUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Calendar;
import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {

    @Query("select au from AuthUser au " +
//            "join au.unit " +
//            "left join au.createdBy " +
            "where " +
//            "au.unit.id = ?#{principal?.unitId} " +
//            "and " +
            "au.deleted = false")
    Page<AuthUser> findAll(Pageable pageable);

    @Query("select au from AuthUser au" +
//            " join au.unit " +
            " where au.id = :id " +
            " and au.deleted = false")
    AuthUser findOne(@Param("id") Long id);

    @Query("select au from AuthUser au " +
//            " join fetch au.unit " +
            "where au.login = :login")
    Optional<AuthUser> findOneByLogin(@Param("login") String login);

    @IsAuthenticated
    @Query("select au from AuthUser au " +
//            "join fetch au.unit " +
            " where au.id = ?#{principal?.id}")
    Optional<AuthUser> findOneCurrentlyAuthenticated();

    @Modifying(clearAutomatically = true)
    @Query("update AuthUser au set au.lastLogin = :lastLogin " +
            "where au.id = :id")
    void updateLastLogin(@Param("id") Long id, @Param("lastLogin") Calendar lastLogin);

}
