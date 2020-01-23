package eu.fp7.driver.ost.core.dto;

import java.util.List;

public class PageDto<T_Dto extends EntityDto> {

    private long total;

    private List<T_Dto> data;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T_Dto> getData() {
        return data;
    }

    public void setData(List<T_Dto> data) {
        this.data = data;
    }
}