package pl.com.itti.app.driver.dto;

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
public final class ImportExcelTriaPositionDTO {
  long questionSetId;
  String stageName;
  String roleName;
  String question;
  String description;
  String dimension;
  int position;
  Boolean requiered;
  String answerType;
  int Comments;

  @Builder.Default
  List<ImportExcelTrialQuestionDTO> excelAmsewrs = new ArrayList<>();

}
