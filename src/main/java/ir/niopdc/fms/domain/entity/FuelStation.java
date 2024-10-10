package ir.niopdc.fms.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@ToString
@Table(name = "fuel_station")
public class FuelStation {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "zone_id")
    private String zoneId;

    @Column(name = "area_id")
    private String areaId;

    @Column(name = "city_id")
    private String cityId;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private Character type;

    @Column(name = "address")
    private String address;

    @Column(name = "incharge_man")
    private String inchargeMan;

    @Column(name = "email")
    private String email;

    @Column(name = "contact_man")
    private String contactMan;

    @Column(name = "contact_telephone")
    private String contactTelephone;

    @Column(name = "telephone1")
    private String telephone1;

    @Column(name = "telephone2")
    private String telephone2;

    @Column(name = "fax")
    private String fax;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "open_date")
    private Date openDate;

    @Column(name = "init_count")
    private int initCount;

    @Column(name = "dialup_sign")
    private Character dialupSign;

    @Column(name = "dailysettle_begin")
    private String dailySettleBegin;

    @Column(name = "dailysettle_end")
    private String dailySettleEnd;

    @Column(name = "owner")
    private String owner;

    @Column(name = " init_status")
    private Character initStatus;

    @Column(name = "validity")
    private Character validity;

    @OneToMany(mappedBy = "fuelStation",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<FuelTerminal> fuelTerminals;


    public void addFuelTerminal(FuelTerminal fuelTerminal) {
        if (this.fuelTerminals == null) {
            this.fuelTerminals = new ArrayList<>();
        }
        fuelTerminal.setFuelStation(this);
        this.fuelTerminals.add(fuelTerminal);
    }

    public void removeFuelTerminal(FuelTerminal fuelTerminal) {
        this.fuelTerminals.remove(fuelTerminal);
    }


}
