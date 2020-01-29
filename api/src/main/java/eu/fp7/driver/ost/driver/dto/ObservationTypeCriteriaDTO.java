package eu.fp7.driver.ost.driver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class ObservationTypeCriteriaDTO {
   private Long trialId;
   private Long trialStageId;
   private Long trialRoleId;
   private Long trialSessionId;
}
