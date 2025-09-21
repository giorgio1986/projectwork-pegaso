package it.previsuite.bean.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.previsuite.bean.ErrorBean;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PreviErrorResponseBean implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("errors")
    private final Set<ErrorBean> errors = new HashSet<>();

    public Set<ErrorBean> getErrors() {
        return errors;
    }
    public void addIssue(ErrorBean issue) {
        this.errors.add(issue);
    }
    public void addIssues(Collection<ErrorBean> issues) {
        if (issues != null) {
            this.errors.addAll(issues);
        }
    }

    public String printErrors() {
        return errors
                .stream()
                .map(error -> Optional.ofNullable(error.getMessage()).orElse("").toString())
                .collect(Collectors.joining(","));
    }
}