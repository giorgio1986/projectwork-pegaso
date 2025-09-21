package it.previsuite.bean.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.previsuite.bean.enums.MemberAddressTypeEnum;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MemberAddressesRequestBean {

    @JsonProperty("addressType")
    private MemberAddressTypeEnum addressType;

    @JsonProperty("nation")
    private String nation;

    @JsonProperty("province")
    private String province;

    @JsonProperty("location")
    private String location;

    @JsonProperty("address")
    private String address;

    public MemberAddressTypeEnum getAddressType() {
        return addressType;
    }
    public void setAddressType(MemberAddressTypeEnum addressType) {
        this.addressType = addressType;
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

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}