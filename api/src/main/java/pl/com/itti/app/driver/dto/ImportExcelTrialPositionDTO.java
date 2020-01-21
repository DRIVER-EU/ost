package pl.com.itti.app.driver.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public final class ImportExcelTrialPositionDTO {
  private Long questionSetId;
  private String stageName;
  private String roleName;
  private String question;
  private String description;
  private String dimension;
  private int position;
  private Boolean required;
  private String answerType;
  private int comments;
  private String jsonSchema;
  @Builder.Default
  private List<ImportExcelTrialAnswerDTO> excelAnswers = new ArrayList<>();

}
