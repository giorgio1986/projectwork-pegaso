package it.previsuite.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class ErrorBean implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("data")
    private Object message = "";

    @JsonProperty("severity")
    private Integer severity = ERROR_SEVERITY.HIGH.getValue();

    public ErrorBean() {
    }
    public ErrorBean(Integer code, Object message, Integer severity) {
        this.code = code;
        this.message = message;
        this.severity = severity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message, severity);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        ErrorBean errorView = (ErrorBean) obj;

        return Objects.equals(code, errorView.code) &&
                Objects.equals(message, errorView.message) &&
                Objects.equals(severity, errorView.severity);
    }

    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getMessage() {
        return message;
    }
    public void setMessage(Object message) {
        this.message = message;
    }

    public Integer getSeverity() {
        return severity;
    }
    public void setSeverity(Integer severity) {
        this.severity = severity;
    }

    public enum ERROR_SEVERITY {
        LOW(0),
        MEDIUM(1),
        HIGH(2);

        private final int severity;

        ERROR_SEVERITY(int severity) {
            this.severity = severity;
        }

        public int getValue() {
            return severity;
        }
    }
}