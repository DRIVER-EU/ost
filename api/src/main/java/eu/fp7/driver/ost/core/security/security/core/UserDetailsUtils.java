package eu.fp7.driver.ost.core.security.security.core;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Utility class for authentication & authorization module.
 */
public abstract class UserDetailsUtils {

    private UserDetailsUtils() {
        throw new AssertionError();
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise
     */
    public static boolean isAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }

    /**
     * If the current user has a specific authority (security role).
     *
     * <p>The name of this method comes from the isUserInRole() method in the Servlet API</p>
     *
     * @param authority the authority to check
     * @return true if the current user has the authority, false otherwise
     */
    public static boolean isCurrentUserInRole(String authority) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication != null) {
            return authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority));
        }

        return false;
    }

    /**
     * If the current user is a member of a specific unit.
     *
     * <p>The name of this method comes from the isUserInRole() method in the Servlet API</p>
     *
     * @param unitId the unit to check
     * @return true if the current user a member of the unit, false otherwise
     */
    public static boolean isCurrentUserInUnit(long unitId) {
        Long currentUserUnitId = getCurrentUserUnitId();
        return currentUserUnitId != null && Long.compare(unitId, currentUserUnitId) == 0;
    }

    /**
     * @return login of the current user if authenticated, null otherwise
     */
    public static String getCurrentUserLogin() {
        return Optional.ofNullable(getCurrentUser())
                .map(UserDetails::getUsername)
                .orElse(null);
    }

    /**
     * @return ID of the current user if authenticated, null otherwise
     */
    public static Long getCurrentUserId() {
        return Optional.ofNullable(getCurrentUser())
                .map(UserDetails::getId)
                .orElse(null);
    }

    /**
     * @return unit ID of the current user if authenticated, null otherwise
     */
    public static Long getCurrentUserUnitId() {
        return Optional.ofNullable(getCurrentUser())
                .map(UserDetails::getUnitId)
                .orElse(null);
    }

    /**
     * Get the current user.
     *
     * @return the currently authenticated user if present, null otherwise
     */
    private static UserDetails getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
                || !(authentication.getPrincipal() instanceof UserDetails)) {
            return null;
        }

        return (UserDetails) authentication.getPrincipal();
    }
}
