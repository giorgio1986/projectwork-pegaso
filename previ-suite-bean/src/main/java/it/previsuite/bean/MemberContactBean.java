package it.previsuite.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MemberContactBean {

    @JsonProperty("email")
    private String email;

    @JsonProperty("emailPec")
    private String emailPec;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("mobilePhone")
    private String mobilePhone;

    @JsonProperty("electronicCommunications")
    private Boolean electronicCommunications;

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

    public String getMobilePhone() {
        return mobilePhone;
    }
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Boolean getElectronicCommunications() {
        return electronicCommunications;
    }
    public void setElectronicCommunications(Boolean electronicCommunications) {
        this.electronicCommunications = electronicCommunications;
    }
}