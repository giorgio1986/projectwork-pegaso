package it.previsuite.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.previsuite.bean.enums.UsersStateEnum;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserBean {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("passwordValidityDays")
    private Integer passwordValidityDays;

    @JsonProperty("state")
    private UsersStateEnum state;

    @JsonProperty("creationTimestamp")
    private String creationTimestamp;

    @JsonProperty("lastAccessTimestamp")
    private String lastAccessTimestamp;

    @JsonProperty("member")
    private MemberBean member;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
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

    public String getCreationTimestamp() {
        return creationTimestamp;
    }
    public void setCreationTimestamp(String creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public String getLastAccessTimestamp() {
        return lastAccessTimestamp;
    }
    public void setLastAccessTimestamp(String lastAccessTimestamp) {
        this.lastAccessTimestamp = lastAccessTimestamp;
    }

    public MemberBean getMember() {
        return member;
    }
    public void setMember(MemberBean member) {
        this.member = member;
    }
}