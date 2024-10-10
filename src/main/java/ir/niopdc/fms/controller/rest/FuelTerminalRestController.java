package ir.niopdc.fms.controller.rest;

import ir.niopdc.fms.entity.dto.FuelTerminalCreateUpdateDTO;
import ir.niopdc.fms.entity.dto.FuelTerminalGetDTO;
import ir.niopdc.fms.entity.service.FuelTerminalService;
import ir.niopdc.fms.facade.FuelTerminalFacadeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class FuelTerminalRestController {

    private final FuelTerminalService fuelTerminalService;
    private final FuelTerminalFacadeService fuelTerminalFacadeService;

    public FuelTerminalRestController(FuelTerminalService theFuelTerminalService,
                                      FuelTerminalFacadeService theFuelTerminalFacadeService) {
        this.fuelTerminalService = theFuelTerminalService;
        this.fuelTerminalFacadeService = theFuelTerminalFacadeService;
    }

    @GetMapping("/fuelTerminals")
    public ResponseEntity<List<FuelTerminalGetDTO>> findAllFuelTerminal() {
        return ResponseEntity.ok(fuelTerminalService.findAllFuelTerminal());
    }

    @GetMapping("/fuelTerminals/{id}")
    public ResponseEntity<FuelTerminalGetDTO> findFuelTerminal(@PathVariable String id) {
        return ResponseEntity.ok(fuelTerminalService.findFuelTerminalById(id));
    }

    @PostMapping("/fuelTerminals")
    public ResponseEntity<String> addFuelTerminal(@Valid @RequestBody FuelTerminalCreateUpdateDTO fuelTerminalRequest) {
        return ResponseEntity.ok(fuelTerminalFacadeService.createFuelTerminal(fuelTerminalRequest));
    }

    @PutMapping("/fuelTerminals")
    public ResponseEntity<String> updateFuelTerminal(@RequestBody FuelTerminalCreateUpdateDTO fuelTerminalRequest) {
        return ResponseEntity.ok(fuelTerminalFacadeService.updateFuelTerminal(fuelTerminalRequest));
    }

    @DeleteMapping("/fuelTerminals/{id}")
    public ResponseEntity<String> deleteFuelTerminal(@PathVariable String id) {
        return ResponseEntity.ok(fuelTerminalService.deleteFuelTerminal(id));
    }

}
