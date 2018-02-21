package pl.com.itti.app.driver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.driver.model.Message;
import pl.com.itti.app.driver.repository.MessageRepository;
import pl.com.itti.app.driver.dto.MessageDTO;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    public Message addMessage(MessageDTO dto) {
        Message message = Message.builder()
                .message(dto.getMessage())
                .role(dto.getRole())
                .selectUser(dto.getSelectUser())
                .dateTime(dto.getTime())
                .build();

        return messageRepository.save(message);
    }
}
