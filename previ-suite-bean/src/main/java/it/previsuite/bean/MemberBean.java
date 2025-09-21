package it.previsuite.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MemberBean {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("fiscalCode")
    private String fiscalCode;

    @JsonProperty("birthDate")
    private String birthDate;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("creationTimestamp")
    private String creationTimestamp;

    @JsonProperty("pensionRegistrationDate")
    private String pensionRegistrationDate;

    @JsonProperty("firstPensionRegistrationDate")
    private String firstPensionRegistrationDate;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }
    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    public String getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCreationTimestamp() {
        return creationTimestamp;
    }
    public void setCreationTimestamp(String creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public String getPensionRegistrationDate() {
        return pensionRegistrationDate;
    }
    public void setPensionRegistrationDate(String pensionRegistrationDate) {
        this.pensionRegistrationDate = pensionRegistrationDate;
    }

    public String getFirstPensionRegistrationDate() {
        return firstPensionRegistrationDate;
    }
    public void setFirstPensionRegistrationDate(String firstPensionRegistrationDate) {
        this.firstPensionRegistrationDate = firstPensionRegistrationDate;
    }
}