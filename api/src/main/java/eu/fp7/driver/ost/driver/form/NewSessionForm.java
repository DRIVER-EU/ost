package eu.fp7.driver.ost.driver.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewSessionForm {

    public long trialId;
    public String initialStage;
    public String prefix;
    public String status;

    public List<UserForm> users;
}
