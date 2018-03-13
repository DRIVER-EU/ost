package pl.com.itti.app.driver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.driver.dto.ObservationDTO;
import pl.com.itti.app.driver.model.Observation;
import pl.com.itti.app.driver.repository.ObservationRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ObservationService {

    @Autowired
    private ObservationRepository observationRepository;

//    public List<Observation> findAll() {
//        return observationRepository.findAll();
//    }
//
//    public Observation addObservation(ObservationDTO dto) {
//        Observation observation = Observation.builder()
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
//        return observationRepository.save(observation);
//    }
}
