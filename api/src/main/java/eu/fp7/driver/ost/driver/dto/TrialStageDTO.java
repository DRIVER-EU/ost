package eu.fp7.driver.ost.driver.dto;

import eu.fp7.driver.ost.core.dto.EntityDto;
import eu.fp7.driver.ost.driver.model.ObservationType;
import eu.fp7.driver.ost.driver.model.TrialStage;

import java.time.LocalDateTime;

public final class TrialStageDTO {

    public static class MinimalItem implements EntityDto<TrialStage> {

        public long id;

        @Override
        public void toDto(TrialStage trialStage) {
            this.id = trialStage.getId();
        }
    }

    public static class ListItem extends MinimalItem {

        public long trialId;
        public String name;
        public LocalDateTime simulationTime;

        @Override
        public void toDto(TrialStage trialStage) {
            super.toDto(trialStage);
            this.trialId = trialStage.getTrial().getId();
            this.name = trialStage.getName();
            this.simulationTime = trialStage.getSimulationTime();
        }
    }

    public static class Statistic extends ListItem {

        public Boolean initHasAnswer = false;
        public long numberOfQuestions;
        public long numberOfQuestionsWithOutAnswer;
        public long numberOfQuestionsWithAnswer;
        public long totalNumberOfAnswers;

        @Override
        public void toDto(TrialStage trialStage) {
            super.toDto(trialStage);
            this.numberOfQuestions = trialStage.getObservationTypes().size();
            this.numberOfQuestionsWithOutAnswer = 0;
            for(ObservationType observationType : trialStage.getObservationTypes())
            {
                long numberOfAnswerForThisQuestion  = observationType.getAnswers().size();
                this.totalNumberOfAnswers = this.totalNumberOfAnswers + numberOfAnswerForThisQuestion;
                if(numberOfAnswerForThisQuestion > 0)
                {
                    this.numberOfQuestionsWithAnswer++;
                }
                else
                {
                    this.numberOfQuestionsWithOutAnswer++;
                }
            }
            if(this.totalNumberOfAnswers >0){
                this.initHasAnswer = true;
            }
        }
    }

    private TrialStageDTO() {
        throw new AssertionError();
    }
}
