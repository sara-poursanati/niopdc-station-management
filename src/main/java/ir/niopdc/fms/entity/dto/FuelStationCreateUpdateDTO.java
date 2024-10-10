package ir.niopdc.fms.entity.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


@Getter
@Setter
@ToString
public class FuelStationCreateUpdateDTO {
    @Id
//    @NotNull(message = "id was not provided.")
    private String id;

//    @NotNull(message = "zone_id was not provided.")
    private String zoneId;

//    @NotNull(message = "area_id was not provided.")
    private String areaId;

//    @NotNull(message = "city_id was not provided.")
    private String cityId;

//    @NotNull(message = "code was not provided.")
    private String code;

//    @NotNull(message = "name was not provided.")
    private String name;

//    @NotNull(message = "type was not provided.")
    private Character type;

//    @NotNull(message = "address was not provided.")
    private String address;

//    @NotNull(message = "incharge_man was not provided.")
    private String inchargeMan;

//    @NotNull(message = "email was not provided.")
    private String email;

//    @NotNull(message = "contact_man was not provided.")
    private String contactMan;
    private String contactTelephone;

//    @NotNull(message = "telephone1 was not provided.")
    private String telephone1;
    private String telephone2;

//    @NotNull(message = "fax was not provided.")
    private String fax;

//    @NotNull(message = "zip_code was not provided.")
    private String zipCode;
    private Date openDate;

//    @NotNull(message = "init_count was not provided.")
    @Max(value = 5, message = "init_count should be less than 6")
    @Min(value = 0, message = "init_count should be more than -1")
    private int initCount;
    private Character dialupSign;
    private String dailySettleBegin;
    private String dailySettleEnd;
    private String owner;
    private Character initStatus;
    private Character validity;

}
