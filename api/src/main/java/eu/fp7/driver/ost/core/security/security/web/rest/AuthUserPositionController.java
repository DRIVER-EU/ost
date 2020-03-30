//package eu.fp7.driver.ost.core.security.security.web.rest;
//
//import eu.fp7.driver.ost.core.annotation.FindAllGetMapping;
//import eu.fp7.driver.ost.core.annotation.IsAdmin;
//import eu.fp7.driver.ost.core.dto.Dto;
//import eu.fp7.driver.ost.core.dto.EnumObjectDto;
//import eu.fp7.driver.ost.core.persistence.db.model.EnumObject;
//import eu.fp7.driver.ost.core.security.security.service.AuthUserPositionService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@IsAdmin
//@Controller
//@RequestMapping("/api/auth/users/positions")
//public class AuthUserPositionController {
//
//    @Autowired
//    private AuthUserPositionService service;
//
//    @FindAllGetMapping
//    public List<EnumObjectDto.Item> findAll() {
//        // TODO change EnumObjectDto & DTO to support wildcard types
//        return Dto.from(service.findAll().stream()
//                        .map(EnumObject.class::cast)
//                        .collect(Collectors.toList()),
//                EnumObjectDto.Item.class);
//    }
//}
