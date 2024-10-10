package ir.niopdc.fms.controller.rest;

import ir.niopdc.fms.domain.dto.FuelStationCreateUpdateDTO;
import ir.niopdc.fms.domain.dto.FuelStationGetDTO;
import ir.niopdc.fms.domain.service.FuelStationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class FuelStationRestController {

    private final FuelStationService fuelStationService;

    public FuelStationRestController(FuelStationService theFuelStationService) {
        this.fuelStationService = theFuelStationService;
    }

    @GetMapping("/fuelStations")
    public ResponseEntity<List<FuelStationGetDTO>> findAllFuelStations() {
        return ResponseEntity.ok(fuelStationService.findAllFuelStation());
    }

    @GetMapping("/fuelStations/{id}")
    public ResponseEntity<FuelStationGetDTO> findFuelStation(@PathVariable String id) {
        return ResponseEntity.ok(fuelStationService.findFuelStationById(id));
    }

    @PostMapping("/fuelStations")
    public ResponseEntity<String> createFuelStation(@Valid @RequestBody FuelStationCreateUpdateDTO fuelStationRequest)
                        throws MethodArgumentNotValidException {
        return ResponseEntity.ok(fuelStationService.createFuelStation(fuelStationRequest));
    }

    @PutMapping("/fuelStations/{id}")
    public ResponseEntity<String> updateFuelStation(@PathVariable String id,
                                                    @RequestBody FuelStationCreateUpdateDTO fuelStationRequest) {
        return ResponseEntity.ok(fuelStationService.updateFuelStation(id, fuelStationRequest));
    }

    @DeleteMapping("/fuelStations/{id}")
    public ResponseEntity<String> deleteFuelStation(@PathVariable String id) {
        fuelStationService.deleteById(id);
        return ResponseEntity.ok("success");
    }

}
