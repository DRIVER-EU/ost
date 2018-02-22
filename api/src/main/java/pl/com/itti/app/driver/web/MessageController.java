package pl.com.itti.app.driver.web;

import co.perpixel.annotation.web.FindAllGetMapping;
import co.perpixel.annotation.web.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.itti.app.driver.dto.MessageDTO;
import pl.com.itti.app.driver.model.Message;
import pl.com.itti.app.driver.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/anonymous/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @FindAllGetMapping
    public List<Message> findAll() {
        return messageService.findAll();
    }

    @PostMapping()
    public ResponseEntity<Message> postMessage(@RequestBody MessageDTO formItem) {
        return new ResponseEntity<>(messageService.addMessage(formItem), HttpStatus.CREATED);
    }
}
