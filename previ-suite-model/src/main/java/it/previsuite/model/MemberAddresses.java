package it.previsuite.model;

import it.previsuite.bean.enums.MemberAddressTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity(name = "MemberAddresses")
@Table(name = "member_addresses", uniqueConstraints = { @UniqueConstraint(columnNames = {"member_id", "address_type"}) })
public class MemberAddresses {

    public interface FIELDS {
        String ID = "id";
        String MEMBER_ID = "memberId";
        String ADDRESS_TYPE = "addressType";
        String NATION = "nation";
        String PROVINCE = "province";
        String LOCATION = "location";
        String ADDRESS = "address";
    }

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(name = "address_type", nullable = false)
    private MemberAddressTypeEnum addressType;

    @Column(name = "nation", nullable = false)
    private String nation;

    @Column(name = "province", nullable = false)
    private String province;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "address", nullable = false)
    private String address;

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

    public MemberAddressTypeEnum getAddressType() {
        return addressType;
    }
    public void setAddressType(MemberAddressTypeEnum addressType) {
        this.addressType = addressType;
    }

    public String getNation() {
        return nation;
    }
    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}