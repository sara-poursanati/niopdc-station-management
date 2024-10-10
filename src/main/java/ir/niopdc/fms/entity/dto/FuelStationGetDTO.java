package ir.niopdc.fms.entity.dto;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class FuelStationGetDTO {
    @Id
    private String id;
    private String zoneId;
    private String areaId;
    private String cityId;
    private String code;
    private String name;
    private Character type;
    private String address;
    private String inchargeMan;
    private String email;
    private String contactMan;
    private String contactTelephone;
    private String telephone1;
    private String telephone2;
    private String fax;
    private String zipCode;
    private Date openDate;
    private int initCount;
    private Character dialupSign;
    private String dailySettleBegin;
    private String dailySettleEnd;
    private String owner;
    private Character initStatus;
    private Character validity;
    private List<FuelTerminalGetDTO> fuelTerminals;

}
