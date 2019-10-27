package pl.com.itti.app.driver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.itti.app.driver.dto.ImportExcelTrialAnswerDTO;
import pl.com.itti.app.driver.dto.ImportExcelTrialDTO;
import pl.com.itti.app.driver.dto.ImportExcelTrialPositionDTO;
import pl.com.itti.app.driver.model.*;
import pl.com.itti.app.driver.model.enums.AnswerType;
import pl.com.itti.app.driver.model.enums.Languages;
import pl.com.itti.app.driver.model.enums.RoleType;
import pl.com.itti.app.driver.repository.*;
import pl.com.itti.app.driver.util.ExcelImportException;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
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
    TrialRoleRepository trialRoleRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    ObservationTypeRoleRepository observationTypeRoleRepository;

    public Trial saveTrial(ImportExcelTrialDTO importExcelTrialDTO) {

        archiveTrailsWithTheSameNameIfTheyAlreadyExists(importExcelTrialDTO);
        Trial trial = createNewTrial(importExcelTrialDTO);

        addStagesToTrial(importExcelTrialDTO, trial);

        for (ImportExcelTrialPositionDTO trailPosition : importExcelTrialDTO.getTrialPositions()) {
            ObservationType observationType = createObservationType(trial, trailPosition);
            createObservationTypeTrialRole(observationType, trial, trailPosition);
            addQuestionsToObservationType(trailPosition, observationType);
            trial.getObservationTypes().add(observationType);
        }

        return trial;
    }

    private TrialStage getTrialStage(Trial trial, ImportExcelTrialPositionDTO trailPosition) {
        Optional<TrialStage> stage = trialStageRepository.findByTrialIdAndName(trial.getId(), trailPosition.getStageName());
        if (!stage.isPresent()) {
            throw new ExcelImportException("Trail can not be connected to the stage - stage missing", trailPosition);
        }
        return stage.get();
    }

    private void addQuestionsToObservationType(ImportExcelTrialPositionDTO trailPosition, ObservationType observationType) {
        for (ImportExcelTrialAnswerDTO excelQuestion : trailPosition.getExcelAnsewrs()) {
            Question question = Question.builder()
                    .observationType(observationType)
                    .answerType(AnswerType.valueOf(trailPosition.getAnswerType()))
                    .commented(false)
                    .description(excelQuestion.getDescription())
                    .jsonSchema(trailPosition.getJsonSchema())
                    .name(excelQuestion.getDescription())
                    .position(excelQuestion.getPosition())
                    .build();
            question = questionRepository.save(question);
            observationType.getQuestions().add(question);

        }
        observationTypeRepository.save(observationType);
    }

    private ObservationType createObservationType(Trial trial, ImportExcelTrialPositionDTO trailPosition) {
        TrialStage stage = getTrialStage(trial, trailPosition);
        ObservationType observationType = ObservationType.builder()
                .description(trailPosition.getDescription())
                .name(trailPosition.getStageName())
                .position(trailPosition.getPosition())
                .trial(trial)
                .trialStage(stage)
                .build();
        observationType = observationTypeRepository.save(observationType);
        return observationType;
    }
    private void archiveTrailsWithTheSameNameIfTheyAlreadyExists(ImportExcelTrialDTO importExcelTrialDTO) {
        try{
            Optional<Trial> trial = trialRepository.findByName(importExcelTrialDTO.getTrialName());
            if( trial.isPresent())
            {
                trial.get().setName( getNewTrailNameForReplacedTrail(trial));
                trial.get().setIsArchived(true);
                trialRepository.save( trial.get());
            }
        }
        catch (Exception e)
        {
            throw new ExcelImportException("Exception while renaming the old trial", e);
        }

    }

    private String getNewTrailNameForReplacedTrail(Optional<Trial> trial) {


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(formatter.format(date));

        int takeNoMoreThen20 = Math.min(trial.get().getName().length(), 20);
        String newTrailName = trial.get().getName().substring(0, takeNoMoreThen20) + " archived @" + formatter.format(date);

        //Trail name at DB is 50 char long
        int takeNoMoreThan50 = Math.min(newTrailName.length(), 50);
        newTrailName = newTrailName.substring(0, takeNoMoreThan50);

        return newTrailName;
    }

    private Trial createNewTrial(ImportExcelTrialDTO importExcelTrialDTO) {
        Trial trial = Trial.builder()
                .description(importExcelTrialDTO.getTrialName())
                .languageVersion(Languages.ENGLISH)
                .name(importExcelTrialDTO.getTrialName())
                .isDefined(true)
                .build();

        trial = trialRepository.save(trial);
        return trial;
    }

    private ObservationTypeTrialRole createObservationTypeTrialRole(ObservationType observationType, Trial trial, ImportExcelTrialPositionDTO trailExcelPosition) {

        TrialRole trialRole = getTrialRoleIfNotExistCreateIt( trial, trailExcelPosition);
        ObservationTypeTrialRole observationTypeTrialRole = ObservationTypeTrialRole.builder()
                .trialRole(trialRole)
                .observationType(observationType)
                .build();

        observationTypeTrialRole = observationTypeRoleRepository.save(observationTypeTrialRole);
        observationType.getObservationTypeTrialRoles().add(observationTypeTrialRole);
        return observationTypeTrialRole;
    }

    private TrialRole getTrialRoleIfNotExistCreateIt(Trial trial, ImportExcelTrialPositionDTO trailPosition) {

        Optional<TrialRole> trialRole = trialRoleRepository.findFirstByTrialIdAndName(trial.id, trailPosition.getRoleName());
        if(trialRole.isPresent())
        {
            return trialRole.get();
        }
        else
        {
            TrialRole  newTrailRole = TrialRole.builder()
                    .trial(trial)
                    .name(trailPosition.getRoleName())
                    //TODO JKW where to find the info in the excel file ?
                    .roleType(RoleType.PARTICIPANT)
                    .build();
            return(trialRoleRepository.save(newTrailRole));
        }
    }

    private  List<TrialStage> addStagesToTrial(ImportExcelTrialDTO importExcelTrialDTO, Trial trial) {
        List<String> uniqueStages = importExcelTrialDTO.getTrialPositions().stream().map(ImportExcelTrialPositionDTO::getStageName).distinct().collect(Collectors.toList());
        for (String stageName : uniqueStages) {
            Optional<TrialStage> trialStage = trialStageRepository.findByTrialIdAndName(trial.id, stageName);
            if (!trialStage.isPresent()) {
                TrialStage newTrialStage = trialStageRepository.save(
                        TrialStage.builder()
                                .name(stageName)
                                .trial(trial)
                                .simulationTime(LocalDateTime.now())
                                .build());
                trial.getTrialStages().add(newTrialStage);
            }
        }
        return trialStageRepository.findAllByTrialId(trial.id);
    }
}