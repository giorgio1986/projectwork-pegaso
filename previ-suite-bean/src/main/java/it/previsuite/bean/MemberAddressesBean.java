package it.previsuite.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.previsuite.bean.request.MemberAddressesRequestBean;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MemberAddressesBean extends MemberAddressesRequestBean {

    @JsonProperty("id")
    private Long id;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}