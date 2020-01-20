package eu.fp7.driver.ost.driver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public final class ImportExcelTrialDTO {
    private String trialName;
    @Builder.Default
    private List<ImportExcelTrialPositionDTO> trialPositions =  new ArrayList<>();
}
