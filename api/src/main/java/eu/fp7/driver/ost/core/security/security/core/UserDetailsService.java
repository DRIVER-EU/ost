//package eu.fp7.driver.ost.core.security.security.core;
//
//import eu.fp7.driver.ost.core.security.security.model.AuthUser;
//import eu.fp7.driver.ost.core.security.security.repository.AuthUserRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.regex.Pattern;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//
//@Component
//@Transactional(readOnly = true)
//public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
//
//    private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);
//
//    @Autowired
//    private AuthUserRepository authUserRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(final String login) {
//        log.debug("Attempting to authenticate '{}'", login);
//
//        if (!Pattern.matches(AuthUser.LOGIN_REGEXP, login)) {
//            throw new UsernameNotFoundException("Username '" + login + "' contains illegal characters");
//        }
//
//        return authUserRepository.findOneByLogin(login)
//                .map(this::createUserDetails)
//                .orElseThrow(() -> new UsernameNotFoundException("User '" + login + "' was not found"));
//    }
//
//    private UserDetails createUserDetails(AuthUser authUser) {
//        return new UserDetails.Builder()
//                .id(authUser.getId())
////                .unitId(authUser.getUnit().getId())
//                .username(authUser.getLogin())
//                .password(authUser.getPassword())
//                .activated(authUser.isActivated())
//                .deleted(isDeleted(authUser))
////                .authorities(getAuthorities(authUser))
//                .build();
//    }
//
//    private boolean isDeleted(AuthUser authUser) {
//        return authUser.isDeleted();
//    }
//
////    private List<GrantedAuthority> getAuthorities(AuthUser authUser) {
////        return authUser.getRoles()
////                .stream()
////                .flatMap(role ->
////                        Stream.concat(
////                                Stream.of(role.getAuthority()),
////                                role.getPermissions()
////                                        .stream()
////                                        .map(GrantedAuthority::getAuthority)
////                        )
////                )
////                .distinct()
////                .map(SimpleGrantedAuthority::new)
////                .collect(Collectors.toList());
////    }
//}
//
