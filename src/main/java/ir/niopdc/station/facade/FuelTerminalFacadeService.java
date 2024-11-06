package ir.niopdc.station.facade;

import ir.niopdc.domain.fuelstation.FuelStation;
import ir.niopdc.domain.fuelstation.FuelStationService;
import ir.niopdc.domain.fuelterminal.FuelTerminal;
import ir.niopdc.domain.fuelterminal.FuelTerminalService;
import ir.niopdc.exceptions.AlreadyExistsException;
import ir.niopdc.exceptions.NotFoundException;
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

    public String createFuelTerminal(FuelTerminal fuelTerminalRequest) {

        // check no terminal exists with this id
        if (fuelTerminalService.existsById(fuelTerminalRequest.getId())) {
            throw new AlreadyExistsException("Terminal is already exist with this id: " + fuelTerminalRequest.getId());
        }

        // try to find the concrete fuel station
        FuelStation fuelStation = fuelStationService.findById(fuelTerminalRequest.getId().getStationId());

        fuelTerminalService.createFuelTerminal(fuelTerminalRequest);

        return "success";
    }

    public String updateFuelTerminal(FuelTerminal fuelTerminalRequest) {
        // check object's existance
        if (!fuelStationService.existsById(fuelTerminalRequest.getId().getStationId())) {
            throw new NotFoundException(fuelTerminalRequest.getId().getStationId());
        }

        // save the changed obj to DB
        fuelTerminalService.updateFuelTerminal(fuelTerminalRequest);

        return "success";
    }

    public List<FuelTerminal> findAllFuelTerminalsByStationId(String stationId) {
        FuelStation station = fuelStationService.findFuelStationById(stationId);

        return fuelTerminalService.findFuelTerminalsByStationId(stationId);
    }

}
