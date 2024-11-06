package ir.niopdc.station.controller.mvc;

import ir.niopdc.domain.fuelstation.FuelStation;
import ir.niopdc.domain.fuelstation.FuelStationService;
import ir.niopdc.station.validators.FuelStationValidator;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/fuelStations")
public class FuelStationController {

    private FuelStationService fuelStationService;

    public FuelStationController(FuelStationService theFuelStationService) {
        this.fuelStationService = theFuelStationService;
    }

    @GetMapping("/list")
    public String showListFuelStation(Model theModel) {
        // get the fuelStations from DB
        List<FuelStation> theFuelStations = fuelStationService.findAll();

        // add to the spring model
        theModel.addAttribute("fuelStations", theFuelStations);

        return "fuelStations/fuelStation-list";
    }

    @GetMapping("/add")
    public String showFormForAdd(Model theModel) {

        // create model attribute to bind form data
        FuelStation theFuelStation = new FuelStation();

        theModel.addAttribute("fuelStation", theFuelStation);

        return "fuelStations/fuelStation-create";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("fuelStationId")  String theId) {

        fuelStationService.deleteById(theId);

        return "redirect:/fuelStations/list";
    }

    @PostMapping("/save")
    public String createFuelStation(
            @Valid @ModelAttribute("fuelStation") FuelStation theFuelStation,
            BindingResult result
    ) throws MethodArgumentNotValidException {

        // check validation
        var validatedResult = FuelStationValidator.createUpdateValidator(theFuelStation, result);

        if (validatedResult.hasErrors()) {
            return "fuelStations/fuelStation-create";
        }

        // save the new fuel station in DB
        fuelStationService.createFuelStation(theFuelStation);

        // use a redirect to prevent duplicate submissions
        return "redirect:/fuelStations/list";
    }

    @PostMapping("/update")
    public String updateFuelStation(
            @Valid @ModelAttribute("fuelStation") FuelStation theFuelStation,
            BindingResult result
            )
    {
        // checking validations
        var validatedResult = FuelStationValidator.createUpdateValidator(theFuelStation, result);

        if (validatedResult.hasErrors()) {
            return "fuelStations/fuelStation-update";
        }

        // save the changed fuel station in DB
        fuelStationService.updateFuelStation(theFuelStation.getId(), theFuelStation);

        // use a redirect to prevent duplicate submissions
        return "redirect:/fuelStations/list";
    }

    @GetMapping("/update")
    public String showFormForUpdate(@RequestParam("fuelStationId") String theId, Model theModel) {
        // get the fuel station from svc
        FuelStation theFuelStation = fuelStationService.findFuelStationById(theId);

        // set fuel station in model to populate the form
        theModel.addAttribute("fuelStation", theFuelStation);

        // send over to our form
        return "fuelStations/fuelStation-update";
    }


    @GetMapping("/info")
    public String showInfoPage(@RequestParam("fuelStationId") String theId, Model theModel) {
        // get the fuel station from svc
        FuelStation theFuelStation = fuelStationService.findFuelStationById(theId);

        // set fuel station in model to provide a ctx
        theModel.addAttribute("fuelStation", theFuelStation);

        // send over to our form
        return "fuelStations/fuelStation-info";
    }


}
