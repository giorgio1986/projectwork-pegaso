package it.previsuite.model;

import it.previsuite.bean.enums.MemberBeneficiaryTypeEnum;
import it.previsuite.bean.enums.MemberGenderEnum;
import it.previsuite.converter.MemberGenderEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.sql.Date;

@Entity(name = "MemberBeneficiary")
@Table(name = "member_beneficiary", uniqueConstraints = { @UniqueConstraint(columnNames = {"member_id", "fiscal_code"}) })
public class MemberBeneficiary {

    public interface FIELDS {
        String ID = "id";
        String MEMBER_ID = "memberId";
        String BENEFICIARY_TYPE = "beneficiaryType";
        String NAME = "name";
        String SURNAME = "surname";
        String FISCAL_CODE = "fiscalCode";
        String BIRTH_DATE = "birthDate";
        String GENDER = "gender";
        String BIRTH_NATION = "birthNation";
        String BIRTH_PROVINCE = "birthProvince";
        String BIRTH_LOCATION = "birthLocation";
        String NATION = "nation";
        String PROVINCE = "province";
        String LOCATION = "location";
        String ADDRESS = "address";
        String COMPANY_NAME = "companyName";
        String VAT_NUMBER = "vatNumber";
        String EMAIL = "email";
        String EMAIL_PEC = "emailPec";
        String PHONE = "phone";
        String MOBILE_PHONE = "mobilePhone";
    }

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(name = "beneficiary_type", nullable = false)
    private MemberBeneficiaryTypeEnum beneficiaryType;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "fiscal_code", nullable = false)
    private String fiscalCode;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "gender")
    @Convert(converter = MemberGenderEnumConverter.class)
    private MemberGenderEnum gender;

    @Column(name = "birth_nation")
    private String birthNation;

    @Column(name = "birth_province")
    private String birthProvince;

    @Column(name = "birth_location")
    private String birthLocation;

    @Column(name = "nation")
    private String nation;

    @Column(name = "province")
    private String province;

    @Column(name = "location")
    private String location;

    @Column(name = "address")
    private String address;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "vat_number")
    private String vatNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "email_pec")
    private String emailPec;

    @Column(name = "phone")
    private String phone;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public MemberBeneficiaryTypeEnum getBeneficiaryType() {
        return beneficiaryType;
    }
    public void setBeneficiaryType(MemberBeneficiaryTypeEnum beneficiaryType) {
        this.beneficiaryType = beneficiaryType;
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

    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public MemberGenderEnum getGender() {
        return gender;
    }
    public void setGender(MemberGenderEnum gender) {
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
}