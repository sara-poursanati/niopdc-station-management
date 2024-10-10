package ir.niopdc.fms.entity.service;

import ir.niopdc.fms.entity.dto.FuelStationCreateUpdateDTO;
import ir.niopdc.fms.entity.dto.FuelStationGetDTO;
import ir.niopdc.fms.entity.entity.FuelStation;
import ir.niopdc.fms.entity.repository.FuelStationRepository;
import ir.niopdc.fms.exceptions.AlreadyExistsException;
import ir.niopdc.fms.exceptions.NotFoundException;
import ir.niopdc.fms.mapper.FuelStationMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.List;

@Service
public class FuelStationService extends BaseService<FuelStationRepository, FuelStation, String> {

    public FuelStationService(FuelStationRepository theFuelStationRepository) {
       setRepository(theFuelStationRepository);
    }

    public FuelStationGetDTO findFuelStationById(String theId) {
        var fuelStation = getRepository().findById(theId).orElseThrow(NotFoundException::new);
        return FuelStationMapper.mapToFuelStationGetDTO(fuelStation);
    }

    public List<FuelStationGetDTO> findAllFuelStation() {
        var result =
                FuelStationMapper.mapToFuelStationGetDTOs(getRepository().findAllByOrderByOpenDate());
        return result;
    }

    public String updateFuelStation(String id, FuelStationCreateUpdateDTO fuelStationRequest) {
        // check object's existance
        if (!getRepository().existsById(id)) {
            throw new NotFoundException("No fuel station is available with this id");
        }

        // convert DTO obj to entity
        var fuelStation = FuelStationMapper.mapToFuelStation(fuelStationRequest);

        // save changed obj in DB
        getRepository().save(fuelStation);
        return "success";
    }

    public String createFuelStation(FuelStationCreateUpdateDTO fuelStationRequest)
                                                        throws MethodArgumentNotValidException {
        // convert DTO obj to entity
        var fuelStation = FuelStationMapper.mapToFuelStation(fuelStationRequest);

        // make sure about not existing the id of new obj
        if (getRepository().existsById(fuelStationRequest.getId())){
            throw new AlreadyExistsException("The fuelStation already exists");
        }

        try {
            // save new obj in DB
            getRepository().save(fuelStation);
            return "success";

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
