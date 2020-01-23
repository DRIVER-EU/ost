package pl.com.itti.app.core.security.security.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.com.itti.app.core.annotation.FindAllGetMapping;
import pl.com.itti.app.core.annotation.IsAdmin;
import pl.com.itti.app.core.dto.DictionaryObjectDto;
import pl.com.itti.app.core.dto.Dto;
import pl.com.itti.app.core.persistence.db.model.DictionaryObject;
import pl.com.itti.app.core.security.security.service.AuthRoleService;

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
