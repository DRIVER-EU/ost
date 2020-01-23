package eu.fp7.driver.ost.core.security.security.web.rest;

import eu.fp7.driver.ost.core.annotation.FindAllGetMapping;
import eu.fp7.driver.ost.core.annotation.IsAdmin;
import eu.fp7.driver.ost.core.dto.DictionaryObjectDto;
import eu.fp7.driver.ost.core.dto.Dto;
import eu.fp7.driver.ost.core.persistence.db.model.DictionaryObject;
import eu.fp7.driver.ost.core.security.security.service.AuthRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@IsAdmin
@Controller
@RequestMapping("/api/auth/roles")
public class AuthRoleController {

    @Autowired
    private AuthRoleService service;

    @FindAllGetMapping
    public List<DictionaryObjectDto.Item> findAll() {
        // TODO change DictionaryObjectDTO & DTO to support wildcard types
        return Dto.from(service.findAll().stream()
                        .map(DictionaryObject.class::cast)
                        .collect(Collectors.toList()),
                DictionaryObjectDto.Item.class);
    }
}
