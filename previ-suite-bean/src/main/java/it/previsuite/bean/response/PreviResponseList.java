package it.previsuite.bean.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PreviResponseList<T> extends PreviResponse<List<T>> {

    @JsonProperty("total")
    private Long total;

    public PreviResponseList() {

    }

    public PreviResponseList(List<T> data) {
        super(data);

        if(data != null) {
            this.total = (long) data.size();
        }
    }

    @Override
    public void setData(List<T> data) {
        this.total = null;

        if(data != null) {
            this.total = (long) data.size();
        }

        super.setData(data);
    }

    public Long getTotal() {
        return total;
    }
}
