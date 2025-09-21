package it.previsuite.model;

import it.previsuite.bean.enums.UsersStateEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.sql.Timestamp;

@Entity(name = "Users")
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "username") })
public class Users {

    public interface FIELDS {
        String ID = "id";
        String MEMBER_ID = "memberId";
        String USERNAME = "username";
        String PASSWORD = "password";
        String PASSWORD_VALIDITY_DAYS = "passwordValidityDays";
        String STATE = "state";
        String LOGIN_ATTEMPTS = "loginAttempts";
        String CREATION_TIMESTAMP = "creationTimestamp";
        String LAST_PASSWORD_RESET_TIMESTAMP = "lastPasswordResetTimestamp";
        String LAST_ACCESS_TIMESTAMP = "lastAccessTimestamp";
        String MEMBER = "members";
    }

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "password_validity_days", nullable = false)
    private Integer passwordValidityDays;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private UsersStateEnum state;

    @Column(name = "login_attempts")
    private Integer loginAttempts;

    @Column(name = "creation_timestamp", nullable = false)
    private Timestamp creationTimestamp;

    @Column(name = "last_password_reset_timestamp")
    private Timestamp lastPasswordResetTimestamp;

    @Column(name = "last_access_timestamp")
    private Timestamp lastAccessTimestamp;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id", insertable=false, updatable=false)
    private Members members;

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

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPasswordValidityDays() {
        return passwordValidityDays;
    }
    public void setPasswordValidityDays(Integer passwordValidityDays) {
        this.passwordValidityDays = passwordValidityDays;
    }

    public UsersStateEnum getState() {
        return state;
    }
    public void setState(UsersStateEnum state) {
        this.state = state;
    }

    public Integer getLoginAttempts() {
        return loginAttempts;
    }
    public void setLoginAttempts(Integer loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    public Timestamp getCreationTimestamp() {
        return creationTimestamp;
    }
    public void setCreationTimestamp(Timestamp creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public Timestamp getLastPasswordResetTimestamp() {
        return lastPasswordResetTimestamp;
    }
    public void setLastPasswordResetTimestamp(Timestamp lastPasswordResetTimestamp) {
        this.lastPasswordResetTimestamp = lastPasswordResetTimestamp;
    }

    public Timestamp getLastAccessTimestamp() {
        return lastAccessTimestamp;
    }
    public void setLastAccessTimestamp(Timestamp lastAccessTimestamp) {
        this.lastAccessTimestamp = lastAccessTimestamp;
    }

    public Members getMember() {
        return members;
    }
    public void setMember(Members members) {
        this.members = members;
    }
}