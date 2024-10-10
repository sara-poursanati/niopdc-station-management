package ir.niopdc.fms.entity.service;

import ir.niopdc.fms.entity.dto.FuelTerminalCreateUpdateDTO;
import ir.niopdc.fms.entity.dto.FuelTerminalGetDTO;
import ir.niopdc.fms.entity.entity.FuelStation;
import ir.niopdc.fms.entity.entity.FuelTerminal;
import ir.niopdc.fms.entity.repository.FuelTerminalRepository;
import ir.niopdc.fms.exceptions.NotFoundException;
import ir.niopdc.fms.mapper.FuelTerminalMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuelTerminalService extends BaseService<FuelTerminalRepository, FuelTerminal, String> {

    public FuelTerminalService(FuelTerminalRepository theFuelTerminalRepository) {
        setRepository(theFuelTerminalRepository);
    }

    public String createFuelTerminal(FuelTerminalCreateUpdateDTO fuelTerminalRequest,
                                     FuelStation fuelStation) {
        // try to make new fuel terminal base on the current station
        try {
            // convert DTO to entity
            FuelTerminal fuelTerminal = FuelTerminalMapper.mapToFuelTerminal(fuelTerminalRequest);

            // add the current station to fuel terminal obj
            fuelTerminal.setFuelStation(fuelStation);

            // save the obj to DB
            getRepository().save(fuelTerminal);
            return "success";

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateFuelTerminal(FuelTerminalCreateUpdateDTO fuelTerminalRequest) {
        try {
            // convert DTO to entity
            var fuelTerminal = FuelTerminalMapper.mapToFuelTerminal(fuelTerminalRequest);

            // save the changed obj to DB
            getRepository().save(fuelTerminal);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String deleteFuelTerminal(String theId) {
        // retrieve fuel terminal
        var fuelterminal = getRepository().findById(theId).orElseThrow(()
                -> new NotFoundException(theId));

        // remove relation between the fuel terminal and its fuel station
        fuelterminal.setFuelStation(null);

        // delete
        getRepository().delete(fuelterminal);
        return "success";
    }

    public FuelTerminalGetDTO findFuelTerminalById(String theId) {
        // find
        var fuelTerminal = getRepository().findById(theId).orElseThrow(()
                -> new NotFoundException(theId));

        // convert entity to DTO
        var result = FuelTerminalMapper.mapToFuelTerminalGetDTO(fuelTerminal);

        return result;
    }

    public List<FuelTerminalGetDTO> findAllFuelTerminal() {
        return FuelTerminalMapper.mapToFuelTerminalGetDTOs(getRepository().findAll());
    }

}
