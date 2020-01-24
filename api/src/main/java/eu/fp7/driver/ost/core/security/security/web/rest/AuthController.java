package eu.fp7.driver.ost.core.security.security.web.rest;

import eu.fp7.driver.ost.core.dto.Dto;
import eu.fp7.driver.ost.core.security.security.repository.AuthUserRepository;
import eu.fp7.driver.ost.core.security.security.web.dto.AuthUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthUserRepository authUserRepository;

    /**
     * POST  /login : check if the user is authenticated, and return its basic profile.
     *
     * @param request the HTTP request
     * @return the basic profile if the user is authenticated
     */
    @GetMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthUserDto.LoginResponse login(HttpServletRequest request) {
        LOG.debug("Check if the '{}' user is authenticated", request.getRemoteUser());
        return authUserRepository.findOneCurrentlyAuthenticated()
                .map(user -> Dto.from(user, AuthUserDto.LoginResponse.class))
                .orElseThrow(() -> new UsernameNotFoundException("User " + request.getRemoteUser() + " was not found"));
    }
}
