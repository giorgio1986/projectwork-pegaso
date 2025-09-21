package it.previsuite.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MemberBeneficiaryNaturalEntityBean extends MemberBeneficiaryBaseBean {

    @JsonIgnore
    public static final String TYPE = "NATURAL_ENTITY";

    @JsonProperty("name")
    private String name;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("birthDate")
    private String birthDate;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("birthNation")
    private String birthNation;

    @JsonProperty("birthProvince")
    private String birthProvince;

    @JsonProperty("birthLocation")
    private String birthLocation;

    @JsonProperty("mobilePhone")
    private String mobilePhone;

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

    public String getBirthNation() {
        return birthNation;
    }
    public void setBirthNation(String birthNation) {
        this.birthNation = birthNation;
    }

    public String getBirthProvince() {
        return birthProvince;
    }
    public void setBirthProvince(String birthProvince) {
        this.birthProvince = birthProvince;
    }

    public String getBirthLocation() {
        return birthLocation;
    }
    public void setBirthLocation(String birthLocation) {
        this.birthLocation = birthLocation;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
}