package it.previsuite.bean.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.previsuite.bean.MemberBeneficiaryLegalEntityBean;
import it.previsuite.bean.MemberBeneficiaryNaturalEntityBean;
import it.previsuite.bean.enums.MemberBeneficiaryTypeEnum;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "beneficiaryType",
        defaultImpl = MemberBeneficiariesRequestBean.class,
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MemberBeneficiaryNaturalEntityBean.class, name = MemberBeneficiaryNaturalEntityBean.TYPE),
        @JsonSubTypes.Type(value = MemberBeneficiaryLegalEntityBean.class, name = MemberBeneficiaryLegalEntityBean.TYPE)
})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MemberBeneficiariesRequestBean {

    @JsonProperty("beneficiaryType")
    private MemberBeneficiaryTypeEnum beneficiaryType;

    @JsonProperty("fiscalCode")
    private String fiscalCode;

    @JsonProperty("nation")
    private String nation;

    @JsonProperty("province")
    private String province;

    @JsonProperty("location")
    private String location;

    public MemberBeneficiaryTypeEnum getBeneficiaryType() {
        return beneficiaryType;
    }
    public void setBeneficiaryType(MemberBeneficiaryTypeEnum beneficiaryType) {
        this.beneficiaryType = beneficiaryType;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }
    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    public String getNation() {
        return nation;
    }
    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
}