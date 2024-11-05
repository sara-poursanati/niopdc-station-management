package ir.niopdc.fms.facade;

import ir.niopdc.fms.domain.dto.FuelStationGetDTO;
import ir.niopdc.fms.domain.dto.FuelTerminalCreateUpdateDTO;
import ir.niopdc.fms.domain.dto.FuelTerminalGetDTO;
import ir.niopdc.fms.domain.entity.FuelStation;
import ir.niopdc.fms.domain.service.FuelStationService;
import ir.niopdc.fms.domain.service.FuelTerminalService;
import ir.niopdc.fms.exceptions.AlreadyExistsException;
import ir.niopdc.fms.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuelTerminalFacadeService {

    private FuelTerminalService fuelTerminalService;
    private FuelStationService fuelStationService;

    @Autowired
    private void setFuelTerminalService(FuelTerminalService fuelTerminalService) {
        this.fuelTerminalService = fuelTerminalService;
    }

    @Autowired
    private void setFuelStationService(FuelStationService fuelStationService) {
        this.fuelStationService = fuelStationService;
    }

    public String createFuelTerminal(FuelTerminalCreateUpdateDTO fuelTerminalRequest) {

        // check no terminal exists with this id
        if (fuelTerminalService.existsById(fuelTerminalRequest.getId())) {
            throw new AlreadyExistsException("Terminal is already exist with this id: " + fuelTerminalRequest.getId());
        }

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

    public List<FuelTerminalGetDTO> findAllFuelTerminalsByStationId(String stationId) {
        FuelStationGetDTO station = fuelStationService.findFuelStationById(stationId);

        return station.getFuelTerminals();
    }

}
