package eu.fp7.driver.ost.core.security.security.core;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails,
        CredentialsContainer, Serializable {

    private static final long serialVersionUID = 1L;

    private final Long id;

    private final Long unitId;

    private final String username;
    private final boolean activated;
    private final boolean deleted;
    private final Set<GrantedAuthority> authorities;
    private String password;

    private UserDetails(Builder builder) {
        id = builder.id;
        unitId = builder.unitId;
        username = builder.username;
        password = builder.password;
        activated = builder.activated;
        deleted = builder.deleted;
        authorities = Collections.unmodifiableSortedSet(sortAuthorities(builder.authorities));
    }

    private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");

        // Ensure array iteration order is predictable (as per
        // UserDetails.getAuthorities() contract and SEC-717)
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<>(new AuthorityComparator());

        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }

        return sortedAuthorities;
    }

    public Long getId() {
        return id;
    }

    public Long getUnitId() {
        return unitId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !deleted;
    }

    @Override
    public boolean isAccountNonLocked() {
        return activated;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !deleted;
    }

    @Override
    public boolean isEnabled() {
        return activated && !deleted;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public void eraseCredentials() {
        password = null;
    }

    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {

        private static final long serialVersionUID = 1L;

        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            // Neither should ever be null as each entry is checked
            // before adding it to the set. If the authority is null,
            // it is a custom authority and should precede others.

            if (g2.getAuthority() == null) {
                return -1;
            }

            if (g1.getAuthority() == null) {
                return 1;
            }

            return g1.getAuthority().compareTo(g2.getAuthority());
        }
    }

    static final class Builder {

        private Long id;

        private Long unitId;

        private String username;

        private String password;

        private boolean activated;

        private boolean deleted;

        private Collection<? extends GrantedAuthority> authorities;

        Builder() {
        }

        Builder id(Long val) {
            id = val;
            return this;
        }

        Builder unitId(Long val) {
            unitId = val;
            return this;
        }

        Builder username(String val) {
            username = val;
            return this;
        }

        Builder password(String val) {
            password = val;
            return this;
        }

        Builder activated(boolean val) {
            activated = val;
            return this;
        }

        Builder deleted(boolean val) {
            deleted = val;
            return this;
        }

        Builder authorities(Collection<? extends GrantedAuthority> val) {
            authorities = val;
            return this;
        }

        UserDetails build() {
            if (username == null || "".equals(username) || password == null) {
                throw new IllegalArgumentException("Username and password are required");
            }

            return new UserDetails(this);
        }
    }
}