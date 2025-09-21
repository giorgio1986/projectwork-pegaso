package it.previsuite.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MemberBeneficiaryLegalEntityBean extends MemberBeneficiaryBaseBean {

    @JsonIgnore
    public static final String TYPE = "LEGAL_ENTITY";

    @JsonProperty("companyName")
    private String companyName;

    @JsonProperty("vatNumber")
    private String vatNumber;

    @JsonProperty("address")
    private String address;

    @JsonProperty("email")
    private String email;

    @JsonProperty("emailPec")
    private String emailPec;

    @JsonProperty("phone")
    private String phone;

    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getVatNumber() {
        return vatNumber;
    }
    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailPec() {
        return emailPec;
    }
    public void setEmailPec(String emailPec) {
        this.emailPec = emailPec;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}