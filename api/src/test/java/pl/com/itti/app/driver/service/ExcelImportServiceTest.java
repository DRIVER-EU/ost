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
import pl.com.itti.app.driver.dto.*;
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


        List<ImportExcelTrialAnswerDTO> importExcelTrialQuestionDTOList  = new ArrayList<ImportExcelTrialAnswerDTO>();
        importExcelTrialQuestionDTOList.add(ImportExcelTrialAnswerDTO.builder().position(1).description("description").build());
        importExcelTrialQuestionDTOList.add(ImportExcelTrialAnswerDTO.builder().position(2).description("description").build());


        ImportExcelTrialPositionDTO trailPosition = ImportExcelTrialPositionDTO.builder()
                .questionSetId(1L)
                .stageName("Block1_Emergent_Groups_Telegram")
                .roleName("CrowdTasker_Observer")
                .question("How many years of professional experience do you have in crisis management? ")
                .description("description ")
                .dimension("CM")
                .position(1)
                .required(true)
                .answerType("RADIO_BUTTON")
                .comments(1)
                .excelAnswers(importExcelTrialQuestionDTOList)
                .build();


        List<ImportExcelTrialPositionDTO> positionList= new ArrayList<>();

        positionList.add(trailPosition);


        ImportExcelTrialDTO trialDTO =  ImportExcelTrialDTO.builder().trialName("Trial Austria").trialPositions(positionList).build();

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