package pl.com.itti.app.driver.form;

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

    public String trialName;
    public String initialStage;
    public String prefix;
    public String status;

    public List<UserForm> users;
}
