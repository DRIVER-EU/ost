package pl.com.itti.app.driver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObservationDTO {
    private String name;
    private String selectUser;
    private String role;
    private String observationType;
    private String who;
    private String what;
    private String attachment;
    private String dateTime;
}
