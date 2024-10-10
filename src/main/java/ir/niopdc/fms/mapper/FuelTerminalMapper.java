package ir.niopdc.fms.mapper;

import ir.niopdc.fms.domain.dto.FuelTerminalCreateUpdateDTO;
import ir.niopdc.fms.domain.dto.FuelTerminalGetDTO;
import ir.niopdc.fms.domain.entity.FuelTerminal;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;


public class FuelTerminalMapper {

    public static FuelTerminalGetDTO mapToFuelTerminalGetDTO(FuelTerminal fuelTerminal){
        FuelTerminalGetDTO result = new FuelTerminalGetDTO();
        BeanUtils.copyProperties(fuelTerminal, result);
        result.setFuelStationId(fuelTerminal.getFuelStation().getId());
        return result;
    }

    public static List<FuelTerminalGetDTO> mapToFuelTerminalGetDTOs(List<FuelTerminal> fuelTerminals) {
        List<FuelTerminalGetDTO> fuelTerminalGetDTOList = fuelTerminals.stream()
                .map(fuelTerminal -> mapToFuelTerminalGetDTO(fuelTerminal))
                .collect(Collectors.toList());
        return fuelTerminalGetDTOList;
    }

    public static FuelTerminal mapToFuelTerminal(FuelTerminalCreateUpdateDTO fuelTerminalCreateUpdateDTO) {
        FuelTerminal fuelTerminal = new FuelTerminal();
        BeanUtils.copyProperties(fuelTerminalCreateUpdateDTO, fuelTerminal);
        return fuelTerminal;
    }
}
