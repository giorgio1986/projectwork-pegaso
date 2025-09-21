package it.previsuite.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.sql.Timestamp;

@Entity(name = "DbMigrations")
@Table(name = "db_migrations", uniqueConstraints = { @UniqueConstraint(columnNames = "script") })
public class DbMigrations {

    public interface FIELDS {
        String ID = "id";
        String SCRIPT = "script";
        String APPLY_TIMESTAMP = "applyTimestamp";
    }

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "script", nullable = false)
    private String script;

    @Column(name = "apply_timestamp", nullable = false)
    private Timestamp applyTimestamp;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getScript() {
        return script;
    }
    public void setScript(String script) {
        this.script = script;
    }

    public Timestamp getApplyTimestamp() {
        return applyTimestamp;
    }
    public void setApplyTimestamp(Timestamp applyTimestamp) {
        this.applyTimestamp = applyTimestamp;
    }
}