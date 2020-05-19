package eu.fp7.driver.ost.driver.web;

import eu.fp7.driver.ost.core.annotation.FindAllGetMapping;
import eu.fp7.driver.ost.core.annotation.PostMapping;
import eu.fp7.driver.ost.core.dto.Dto;
import eu.fp7.driver.ost.core.exception.FormValidationException;
import eu.fp7.driver.ost.driver.dto.KeycloakUserDto;
import eu.fp7.driver.ost.driver.service.AuthService;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/auth/users")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @FindAllGetMapping
    public List<KeycloakUserDto.ListItem> findAll(Pageable pageable) {
        return Dto.from(authService.findAll(pageable), KeycloakUserDto.ListItem.class);
    }

    @GetMapping("/{id}")
    public KeycloakUserDto.FullItem findOne(@PathVariable("id") String id) {
        return Dto.from(authService.findOne(id), KeycloakUserDto.FullItem.class);
    }

    @PostMapping
    public KeycloakUserDto.FullItem create(
            @Validated @RequestBody KeycloakUserDto.CreateFormItem form,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new FormValidationException(KeycloakUserDto.CreateFormItem.class);
        }

        return Dto.from(authService.create(form), KeycloakUserDto.FullItem.class);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        authService.delete(id);
    }
}
