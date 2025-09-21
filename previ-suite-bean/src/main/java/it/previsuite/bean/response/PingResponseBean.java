package it.previsuite.bean.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PingResponseBean {
    @JsonProperty("applicationName")
    private String applicationName;

    @JsonProperty("applicationVersion")
    private String applicationVersion;

    @JsonProperty("currentDate")
    private String currentDate;

    public String getApplicationName() {
        return applicationName;
    }
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }
    public void setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    public String getCurrentDate() {
        return currentDate;
    }
    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }
}