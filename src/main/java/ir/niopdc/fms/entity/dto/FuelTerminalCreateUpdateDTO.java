package ir.niopdc.fms.entity.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class FuelTerminalCreateUpdateDTO {
    @Id
    @NotNull(message = "id is required")
    private String id;

    @NotNull(message = "fuel_station_id is required")
    private String fuelStationId;
    private String fuelSamId;
    private String paySamId;
    private String fuelType;
    private String ipcIpAddr;
    private String ptIpAddr;
    private String nozzleId;
    private String oilcanId;
    private String dispenserType;
    private String fuelPublicKeyN;
    private String fPayPublicKeyN;
    private String sFuelPublicKeyN;
    private String sPayPublicKeyN;
    private String fuelPublicKeyE;
    private String fPayPublicKeyE;
    private String sFuelPublicKeyE;
    private String sPayPublicKeyE;
    private Character initStatus;
    private Character fCardissueStatus;
    private Character pCardissueStatus;
    private String appIssuer;
    private String appReceiver;
    private Date appDateBegin;
    private Date appDateEnd;
    private String stAppIssuer;
    private String stAppReceiver;
    private Date stAppDateBegin;
    private Date stAppDateEnd;
    private String samSeria;
    private String samVer;
    private String cardType;
    private String fciByIssuer;
    private String conKeyIdx;
    private String fBatchName;
    private String pBatchName;
    private Date applyTime;
    private Character validity;

}
