package pl.com.itti.app.driver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.driver.model.Trial;
import pl.com.itti.app.driver.repository.TrialRepository;


@Service
@Transactional
public class TrialService {

    @Autowired
    private TrialRepository trialRepository;

    public Iterable<Trial> getAllTrials() {
        return trialRepository.findAll();
    }

    public Trial getTrialById(Long trialId) {
        Trial trial = trialRepository.findById(trialId).get();
        return trial;
    }



}
