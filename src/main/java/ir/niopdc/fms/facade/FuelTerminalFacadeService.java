package ir.niopdc.fms.facade;

import ir.niopdc.fms.domain.dto.FuelTerminalCreateUpdateDTO;
import ir.niopdc.fms.domain.entity.FuelStation;
import ir.niopdc.fms.domain.service.FuelStationService;
import ir.niopdc.fms.domain.service.FuelTerminalService;
import ir.niopdc.fms.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuelTerminalFacadeService {

    @Autowired
    private FuelTerminalService fuelTerminalService;
    @Autowired
    private FuelStationService fuelStationService;

    public String createFuelTerminal(FuelTerminalCreateUpdateDTO fuelTerminalRequest) {
        // try to find the concrete fuel station
        FuelStation fuelStation = fuelStationService.findById(fuelTerminalRequest.getFuelStationId());

        fuelTerminalService.createFuelTerminal(fuelTerminalRequest, fuelStation);

        return "success";
    }

    public String updateFuelTerminal(FuelTerminalCreateUpdateDTO fuelTerminalRequest) {
        // check object's existance
        if (!fuelStationService.existsById(fuelTerminalRequest.getFuelStationId())) {
            throw new NotFoundException(fuelTerminalRequest.getFuelStationId());
        }

        // save the changed obj to DB
        fuelTerminalService.updateFuelTerminal(fuelTerminalRequest);

        return "success";
    }

}
