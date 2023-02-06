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
public final class ImportExcelTrialPositionDTO {
  private String questionSetName;
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
  private int excelRow;
  @Builder.Default
  private List<ImportExcelTrialAnswerDTO> excelAnswers = new ArrayList<>();

}
