package it.previsuite.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity(name = "MemberDesignatedSubjects")
@Table(name = "member_designated_subjects", uniqueConstraints = { @UniqueConstraint(columnNames = {"member_id", "beneficiary_id"}) })
public class MemberDesignatedSubjects {

    public interface FIELDS {
        String ID = "id";
        String MEMBER_ID = "memberId";
        String BENEFICIARY_ID = "beneficiaryId";
        String DISTRIBUTION = "distribution";
        String ORDERING = "ordering";
        String BENEFICIARY = "beneficiary";
    }

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "beneficiary_id", nullable = false)
    private Long beneficiaryId;

    @Column(name = "distribution", nullable = false)
    private Float distribution;

    @Column(name = "ordering", nullable = false)
    private Integer ordering;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beneficiary_id", referencedColumnName = "id", insertable=false, updatable=false)
    private MemberBeneficiary beneficiary;

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

    public Long getBeneficiaryId() {
        return beneficiaryId;
    }
    public void setBeneficiaryId(Long beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public Float getDistribution() {
        return distribution;
    }
    public void setDistribution(Float distribution) {
        this.distribution = distribution;
    }

    public Integer getOrdering() {
        return ordering;
    }
    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    public MemberBeneficiary getBeneficiary() {
        return beneficiary;
    }
    public void setBeneficiary(MemberBeneficiary beneficiary) {
        this.beneficiary = beneficiary;
    }
}