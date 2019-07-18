package pl.com.itti.app.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageDto<T extends EntityDto> {

    public long total;

    public List<T> data;

}
