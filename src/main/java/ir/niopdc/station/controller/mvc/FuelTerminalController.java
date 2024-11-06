package ir.niopdc.station.controller.mvc;

import ir.niopdc.domain.fuelterminal.FuelTerminal;
import ir.niopdc.domain.fuelterminal.FuelTerminalKey;
import ir.niopdc.domain.fuelterminal.FuelTerminalService;
import ir.niopdc.station.facade.FuelTerminalFacadeService;
import ir.niopdc.station.validators.FuelTerminalValidator;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/fuelTerminals")
@Validated
public class FuelTerminalController {

    private FuelTerminalService fuelTerminalService;

    private FuelTerminalFacadeService fuelTerminalFacadeService;

    // constructor injection
    public FuelTerminalController(FuelTerminalService theFuelTerminalService,
                                  FuelTerminalFacadeService theFuelTerminalFacadeService) {
        this.fuelTerminalService = theFuelTerminalService;
        this.fuelTerminalFacadeService = theFuelTerminalFacadeService;
    }

    @GetMapping("/list")
    public String showListFuelTerminal(@RequestParam("stationId") String stationId, Model theModel) {
        // get the fuelTerminals from DB
        List<FuelTerminal> theFuelTerminals = fuelTerminalFacadeService.findAllFuelTerminalsByStationId(stationId);

        // add stationId to the model for add button in front side
        theModel.addAttribute("stationId", stationId);

        // add to the spring model
        theModel.addAttribute("fuelTerminals", theFuelTerminals);

        return "fuelTerminals/fuelTerminal-list";
    }

    @GetMapping("/add")
    public String showFormForAdd(@Nullable @RequestParam("fuelStationId") String fuelStationId, Model theModel) {

        // create model attribute to bind form data
        FuelTerminal theFuelTerminal = new FuelTerminal();

        // create model attribute for id to bind from data
        FuelTerminalKey fuelTerminalKey = new FuelTerminalKey();

        theModel.addAttribute("fuelTerminal", theFuelTerminal);

        theModel.addAttribute("fuelTerminalKey", fuelTerminalKey);

        theModel.addAttribute("theFuelStationId", fuelStationId);

        return "fuelTerminals/fuelTerminal-create";
    }

    @PostMapping("/save")
    public String createFuelTerminal(
            @Valid @ModelAttribute("fuelTerminal") FuelTerminal theFuelTerminal,
            @ModelAttribute("fuelTerminalKey") FuelTerminalKey fuelTerminalKey,
            BindingResult result,
            Model theModel
            )
    {
        // set the id
        theFuelTerminal.setId(fuelTerminalKey);

        // checking validations
        var validatedResult = FuelTerminalValidator.createUpdateValidator(theFuelTerminal, result);

        if (validatedResult.hasErrors()) {
            return "fuelTerminals/fuelTerminal-create";
        }

        try {
            // save the new fuel terminal in DB
            fuelTerminalFacadeService.createFuelTerminal(theFuelTerminal);
        } catch (Exception e) {
            // send the error message to the front for showing in UI
            theModel.addAttribute("error", e.getMessage());
            return "fuelTerminals/fuelTerminal-create";
        }

        // use a redirect to prevent duplicate submissions
        return "redirect:/fuelTerminals/list?stationId=%s".formatted(theFuelTerminal.getId().getStationId());
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("fuelTerminalId")  String theId, HttpServletRequest request) {

        fuelTerminalService.deleteFuelTerminal(theId);

        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping("/update")
    public String showFormForUpdate(
            @RequestParam("fuelTerminalId") String theId,
            Model theModel
    ) {
        // get the fuel station from svc
        FuelTerminal theFuelTerminal = fuelTerminalService.findFuelTerminalById(theId);

        // set fuel terminal in model to populate the form
        theModel.addAttribute("fuelTerminal", theFuelTerminal);

        // send over to our form
        return "fuelTerminals/fuelTerminal-update";
    }

    @PostMapping("/update")
    public String updateFuelTerminal(
            @Valid @ModelAttribute("fuelTerminal") FuelTerminal theFuelTerminal,
            BindingResult result
    )
    {
        // checking validations
        var validatedResult = FuelTerminalValidator.createUpdateValidator(theFuelTerminal, result);

        if (validatedResult.hasErrors()) {
            return "fuelTerminals/fuelTerminal-update";
        }

        // save the changed fuel terminal in DB
        fuelTerminalService.updateFuelTerminal(theFuelTerminal);

        // use a redirect to prevent duplicate submissions
        return "redirect:/fuelTerminals/list?stationId=%s".formatted(theFuelTerminal.getId().getStationId());
    }

    @GetMapping("/info")
    public String showInfoPage(@RequestParam("fuelTerminalId") String theId, Model theModel) {
        // get the fuel terminal from svc
        FuelTerminal theFuelTerminal = fuelTerminalService.findFuelTerminalById(theId);

        // set fuel terminal in model to provide a ctx
        theModel.addAttribute("fuelTerminal", theFuelTerminal);

        // send over to our form
        return "fuelTerminals/fuelTerminal-info";
    }


}
