package it.previsuite.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.previsuite.bean.request.MemberDesignatedSubjectsRequestBean;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MemberDesignatedSubjectsBean extends MemberDesignatedSubjectsRequestBean {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("beneficiary")
    private MemberBeneficiaryBaseBean beneficiary;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public MemberBeneficiaryBaseBean getBeneficiary() {
        return beneficiary;
    }
    public void setBeneficiary(MemberBeneficiaryBaseBean beneficiary) {
        this.beneficiary = beneficiary;
    }
}