package pl.com.itti.app.core.security.security.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.com.itti.app.core.annotation.FindAllGetMapping;
import pl.com.itti.app.core.annotation.IsAdmin;
import pl.com.itti.app.core.dto.Dto;
import pl.com.itti.app.core.dto.EnumObjectDto;
import pl.com.itti.app.core.persistence.db.model.EnumObject;
import pl.com.itti.app.core.security.security.service.AuthUserPositionService;

import java.util.List;
import java.util.stream.Collectors;

@IsAdmin
@Controller
@RequestMapping("/api/auth/users/positions")
public class AuthUserPositionController {

    @Autowired
    private AuthUserPositionService service;

    @FindAllGetMapping
    public List<EnumObjectDto.Item> findAll() {
        // TODO change EnumObjectDto & DTO to support wildcard types
        return Dto.from(service.findAll().stream()
                        .map(EnumObject.class::cast)
                        .collect(Collectors.toList()),
                EnumObjectDto.Item.class);
    }
}
