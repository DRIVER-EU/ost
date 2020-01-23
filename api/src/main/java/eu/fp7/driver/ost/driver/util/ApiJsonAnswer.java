package eu.fp7.driver.ost.driver.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ApiJsonAnswer {

    @JsonFormat
    private long id;
    @JsonFormat
    private String name;
    @JsonFormat
    private String description;
    @JsonFormat
    private HttpStatus status;
    @JsonFormat
    private List<ApiValidationError> errors = new ArrayList<>();
    @JsonFormat
    private List<ApiValidationWarning> warnings = new ArrayList<>();

    public ApiJsonAnswer() {
    }
}
