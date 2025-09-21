package it.previsuite.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Date;
import java.sql.Timestamp;

@Entity(name = "Questionnaire")
@Table(name = "questionnaire")
public class Questionnaire {

    public interface FIELDS {
        String ID = "id";
        String CREATION_TIMESTAMP = "creationTimestamp";
        String EXPIRY_DATE = "expiryDate";
        String QUESTIONNAIRE = "questionnaire";
    }

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_timestamp", nullable = false)
    private Timestamp creationTimestamp;

    @Column(name = "expiry_date")
    private Date expiryDate;

    @Column(name = "questionnaire", nullable = false)
    private String questionnaire;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCreationTimestamp() {
        return creationTimestamp;
    }
    public void setCreationTimestamp(Timestamp creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getQuestionnaire() {
        return questionnaire;
    }
    public void setQuestionnaire(String questionnaire) {
        this.questionnaire = questionnaire;
    }
}