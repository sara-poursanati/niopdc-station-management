package ir.niopdc.fms.mapper;

import ir.niopdc.fms.domain.dto.FuelStationCreateUpdateDTO;
import ir.niopdc.fms.domain.dto.FuelStationGetDTO;
import ir.niopdc.fms.domain.entity.FuelStation;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.stream.Collectors;

public class FuelStationMapper {

    public static FuelStation mapToFuelStation(FuelStationCreateUpdateDTO fuelStationCreateUpdateDTO) {
        FuelStation fuelStation = new FuelStation();
        BeanUtils.copyProperties(fuelStationCreateUpdateDTO, fuelStation);
        return fuelStation;
    }

    public static FuelStationGetDTO mapToFuelStationGetDTO(FuelStation fuelStation){
        var result = new FuelStationGetDTO();
        BeanUtils.copyProperties(fuelStation, result);
        result.setFuelTerminals(FuelTerminalMapper.mapToFuelTerminalGetDTOs(fuelStation.getFuelTerminals()));
        return result;
    }

    public static List<FuelStationGetDTO> mapToFuelStationGetDTOs(List<FuelStation> fuelStations) {
        List<FuelStationGetDTO> fuelstationGetDTOList = fuelStations.stream()
                .map(fuelStation -> mapToFuelStationGetDTO(fuelStation))
                .collect(Collectors.toList());
        return fuelstationGetDTOList;
    }
}
