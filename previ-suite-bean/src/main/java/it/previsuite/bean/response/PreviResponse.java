package it.previsuite.bean.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PreviResponse<T> extends PreviErrorResponseBean implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public PreviResponse() {
    }
    public PreviResponse(T data) {
        this.data = data;
    }

    @JsonProperty("data")
    private T data;

    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
}