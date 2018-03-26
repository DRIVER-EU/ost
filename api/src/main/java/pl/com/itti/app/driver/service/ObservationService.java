package pl.com.itti.app.driver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.driver.repository.ObservationRepository;

@Service
@Transactional(readOnly = true)
public class ObservationService {

    @Autowired
    private ObservationRepository observationRepository;

//    public List<Answer> findAll() {
//        return observationRepository.findAll();
//    }
//
//    public Answer addObservation(ObservationDTO dto) {
//        Answer answer = Answer.builder()
//                .name(dto.getName())
//                .selectUser(dto.getSelectUser())
//                .role(dto.getRole())
//                .observationType(dto.getObservationType())
//                .who(dto.getWho())
//                .what(dto.getWhat())
//                .attachment(dto.getAttachment())
//                .dateTime(dto.getDateTime())
//                .build();
//
//        return observationRepository.save(answer);
//    }
}
