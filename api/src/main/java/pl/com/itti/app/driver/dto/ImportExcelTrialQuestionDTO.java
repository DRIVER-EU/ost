package pl.com.itti.app.driver.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public final class ImportExcelTrialQuestionDTO {
  Integer position;
  String description;
}
