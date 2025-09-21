package it.previsuite.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity(name = "MemberContacts")
@Table(name = "member_contacts", uniqueConstraints = { @UniqueConstraint(columnNames = "member_id") })
public class MemberContacts {

    public interface FIELDS {
        String ID = "id";
        String MEMBER_ID = "memberId";
        String EMAIL = "email";
        String EMAIL_PEC = "emailPec";
        String PHONE = "phone";
        String MOBILE_PHONE = "mobilePhone";
        String ELECTRONIC_COMMUNICATIONS = "electronicCommunications";
    }

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "email_pec")
    private String emailPec;

    @Column(name = "phone")
    private String phone;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "electronic_communications", nullable = false)
    private Boolean electronicCommunications;

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