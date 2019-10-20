package pl.com.itti.app.driver.dto;

import lombok.*;
import pl.com.itti.app.driver.model.enums.AnswerType;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public final class ImportExcelTrialDTO {
    String TrialName;
    List<ImportExcelTriaPositionDTO> trialPositions =  new ArrayList<>();
}
