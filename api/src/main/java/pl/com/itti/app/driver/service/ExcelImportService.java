package pl.com.itti.app.driver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.driver.dto.ImportExcelTriaPositionDTO;
import pl.com.itti.app.driver.dto.ImportExcelTrialDTO;
import pl.com.itti.app.driver.dto.ImportExcelTrialQuestionDTO;
import pl.com.itti.app.driver.model.ObservationType;
import pl.com.itti.app.driver.model.Question;
import pl.com.itti.app.driver.model.Trial;
import pl.com.itti.app.driver.model.TrialStage;
import pl.com.itti.app.driver.model.enums.AnswerType;
import pl.com.itti.app.driver.model.enums.Languages;
import pl.com.itti.app.driver.repository.ObservationTypeRepository;
import pl.com.itti.app.driver.repository.QuestionRepository;
import pl.com.itti.app.driver.repository.TrialRepository;
import pl.com.itti.app.driver.repository.TrialStageRepository;
import pl.com.itti.app.driver.util.ExcelImportException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExcelImportService {
    //TODO JKW where to find it ?
    public static final long TEST_BED_STAGE_ID = 1L;
    @Autowired
    TrialRepository trialRepository;
    @Autowired
    TrialStageRepository trialStageRepository;

    @Autowired
    ObservationTypeRepository observationTypeRepository;

    @Autowired
    QuestionRepository questionRepository;

    public Trial saveTrial(ImportExcelTrialDTO importExcelTrialDTO) {
        Page<TrialStage> trialStagePage = trialStageRepository.findAll(new PageRequest(1, 10));

        Trial trial = Trial.builder()
                .languageVersion(Languages.ENGLISH)
                .name(importExcelTrialDTO.getTrialName())
                .description(importExcelTrialDTO.getTrialName())
                .build();

        trial = trialRepository.save(trial);

        List<TrialStage> trailStagesList =  extractStages(importExcelTrialDTO, trial);

        trial.getTrialStages().addAll(trailStagesList);
        //List<ObservationType> observationTypeList = new ArrayList<>();
        for (ImportExcelTriaPositionDTO trailPosition : importExcelTrialDTO.getTrialPositions()) {
            Optional<TrialStage> stage = trialStageRepository.findByTrialIdAndName(trial.getId(), trailPosition.getStageName());
            if (!stage.isPresent()) {
                throw new ExcelImportException("Trail can not be connected to the stage - stage missing");
            }

            //Pomyśleć jak zrobic role - czy tam to jest - analiza

            ObservationType observationType = ObservationType.builder()
                    .description(trailPosition.getDescription())
                    //   .observationTypeTrialRoles()
                    //   .multiplicity()
                    //   .name()
                    .position(trailPosition.getPosition())
                    .trial(trial)
                    .trialStage(stage.get())
                    .build();
            observationType = observationTypeRepository.save(observationType);
            trial.getObservationTypes().add(observationType);

            for (ImportExcelTrialQuestionDTO excelQuestion : trailPosition.getExcelAmsewrs()) {
                Question question = Question.builder()
                        .observationType(observationType)
                        .answerType(AnswerType.valueOf(trailPosition.getAnswerType()))
                        .commented(false)
                        .description(excelQuestion.getDescription())
                        .name(excelQuestion.getDescription())
                        .position(excelQuestion.getPosition())
                        .build();
                question = questionRepository.save(question);
                observationType.getQuestions().add(question);

            }
            observationTypeRepository.save(observationType);
        }
        trialRepository.save(trial);
        return trial;
    }

    private  List<TrialStage>  extractStages(ImportExcelTrialDTO importExcelTrialDTO, Trial trial) {
        List<String> uniqueStages = importExcelTrialDTO.getTrialPositions().stream().map(ImportExcelTriaPositionDTO::getStageName).distinct().collect(Collectors.toList());
        for (String stageName : uniqueStages) {
            Optional<TrialStage> trialStage = trialStageRepository.findByTrialIdAndName(trial.id, stageName);
            if (!trialStage.isPresent()) {
                trialStageRepository.save(
                        TrialStage.builder()
                                .name(stageName)
                                .trial(trial)
                                .simulationTime(LocalDateTime.now())
                                .testBedStageId(TEST_BED_STAGE_ID)
                                .build());
            }
        }
        return trialStageRepository.findAllByTrialId(trial.id);
    }
}