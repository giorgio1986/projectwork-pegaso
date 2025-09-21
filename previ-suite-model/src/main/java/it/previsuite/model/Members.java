package it.previsuite.model;

import it.previsuite.bean.enums.MemberGenderEnum;
import it.previsuite.converter.MemberGenderEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;


import java.sql.Date;
import java.sql.Timestamp;

@Entity(name = "Members")
@Table(name = "members", uniqueConstraints = { @UniqueConstraint(columnNames = "fiscal_code") })
public class Members {

    public interface FIELDS {
        String ID = "id";
        String NAME = "name";
        String SURNAME = "surname";
        String FISCAL_CODE = "fiscalCode";
        String BIRTH_DATE = "birthDate";
        String GENDER = "gender";
        String CREATION_TIMESTAMP = "creationTimestamp";
        String PENSION_REGISTRATION_DATE = "pensionRegistrationDate";
        String FIRST_PENSION_REGISTRATION_DATE = "firstPensionRegistrationDate";
    }

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "fiscal_code", nullable = false)
    private String fiscalCode;

    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    @Column(name = "gender", nullable = false)
    @Convert(converter = MemberGenderEnumConverter.class)
    private MemberGenderEnum gender;

    @Column(name = "creation_timestamp", nullable = false)
    private Timestamp creationTimestamp;

    @Column(name = "pension_registration_date", nullable = false)
    private Date pensionRegistrationDate;

    @Column(name = "first_pension_registration_date", nullable = false)
    private Date firstPensionRegistrationDate;

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

    public Timestamp getCreationTimestamp() {
        return creationTimestamp;
    }
    public void setCreationTimestamp(Timestamp creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public Date getPensionRegistrationDate() {
        return pensionRegistrationDate;
    }
    public void setPensionRegistrationDate(Date pensionRegistrationDate) {
        this.pensionRegistrationDate = pensionRegistrationDate;
    }

    public Date getFirstPensionRegistrationDate() {
        return firstPensionRegistrationDate;
    }
    public void setFirstPensionRegistrationDate(Date firstPensionRegistrationDate) {
        this.firstPensionRegistrationDate = firstPensionRegistrationDate;
    }
}