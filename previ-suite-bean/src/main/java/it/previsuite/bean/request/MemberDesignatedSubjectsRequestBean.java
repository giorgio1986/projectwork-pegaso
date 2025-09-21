package it.previsuite.bean.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MemberDesignatedSubjectsRequestBean {

    @JsonProperty("beneficiaryId")
    private Long beneficiaryId;

    @JsonProperty("distribution")
    private Float distribution;

    @JsonProperty("ordering")
    private Integer ordering;

    public Long getBeneficiaryId() {
        return beneficiaryId;
    }
    public void setBeneficiaryId(Long beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public Float getDistribution() {
        return distribution;
    }
    public void setDistribution(Float distribution) {
        this.distribution = distribution;
    }

    public Integer getOrdering() {
        return ordering;
    }
    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }
}