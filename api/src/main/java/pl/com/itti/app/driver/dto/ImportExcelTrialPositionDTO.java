package pl.com.itti.app.driver.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public final class ImportExcelTrialPositionDTO {
  Long questionSetId;
  String stageName;
  String roleName;
  String question;
  String description;
  String dimension;
  int position;
  Boolean requiered;
  String answerType;
  int comments;
  String jsonSchema;
  @Builder.Default
  List<ImportExcelTrialAnswerDTO> excelAnsewrs = new ArrayList<>();

}
