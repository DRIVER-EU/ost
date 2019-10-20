package pl.com.itti.app.driver.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.itti.app.driver.dto.ImportExcelTriaPositionDTO;
import pl.com.itti.app.driver.dto.ImportExcelTrialDTO;
import pl.com.itti.app.driver.dto.ImportExcelTrialQuestionDTO;
import pl.com.itti.app.driver.model.Trial;
import pl.com.itti.app.driver.repository.TrialRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@OverrideAutoConfiguration(enabled = false)
@org.springframework.transaction.annotation.Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@ImportAutoConfiguration
public class ExcelImportServiceTest {
    @Autowired
    ExcelImportService excelImportService;
    @Autowired
    private TrialRepository trialRepository;

    @Test
    public void saveTrial() {
        // given

        List<ImportExcelTrialQuestionDTO> importExcelTrialQuestionDTOList = new ArrayList<ImportExcelTrialQuestionDTO>();
        importExcelTrialQuestionDTOList.add(ImportExcelTrialQuestionDTO.builder().position(1).description("description").build());
        importExcelTrialQuestionDTOList.add(ImportExcelTrialQuestionDTO.builder().position(2).description("description").build());


        ImportExcelTriaPositionDTO trailPosition = ImportExcelTriaPositionDTO.builder()
                .questionSetId(1L)
                .stageName("Block1_Emergent_Groups_Telegram")
                .roleName("CrowdTasker_Observer")
                .question("How many years of professional experience do you have in crisis management? ")
                .description("description ")
                .dimension("CM")
                .position(1)
                .requiered(true)
                .answerType("RADIO_BUTTON")
                .Comments(1)
                .excelAmsewrs(importExcelTrialQuestionDTOList)
                .build();

        List<ImportExcelTriaPositionDTO> positionList = new ArrayList<>();
        positionList.add(trailPosition);

        ImportExcelTrialDTO trialDTO =  ImportExcelTrialDTO.builder().TrialName("Trial Austria").trialPositions(positionList).build();

        // when
        Trial trail = excelImportService.saveTrial(trialDTO);

        Optional<Trial> trialFromRepository = trialRepository.findById(trail.id);
        Assert.assertTrue(trialFromRepository.isPresent());
        Assert.assertEquals("Trial Austria",trialFromRepository.get().getName());
        Assert.assertEquals(1,trialFromRepository.get().getTrialStages().size());
        Assert.assertEquals("Block1_Emergent_Groups_Telegram",trialFromRepository.get().getTrialStages().get(0).getName());
        Assert.assertEquals(2,trialFromRepository.get().getObservationTypes().get(0).getQuestions().size());
    }
}