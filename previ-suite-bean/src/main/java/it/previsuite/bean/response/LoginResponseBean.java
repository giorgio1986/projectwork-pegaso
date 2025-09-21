package it.previsuite.bean.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponseBean {
    @JsonProperty("authToken")
    private String authToken;

    @JsonProperty("authTokenExpirationDate")
    private String authTokenExpirationDate;

    public String getAuthToken() {
        return authToken;
    }
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthTokenExpirationDate() {
        return authTokenExpirationDate;
    }
    public void setAuthTokenExpirationDate(String authTokenExpirationDate) {
        this.authTokenExpirationDate = authTokenExpirationDate;
    }
}