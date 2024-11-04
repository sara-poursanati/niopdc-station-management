package ir.niopdc.fms.controller.mvc;

import ir.niopdc.fms.domain.dto.FuelTerminalCreateUpdateDTO;
import ir.niopdc.fms.domain.dto.FuelTerminalGetDTO;
import ir.niopdc.fms.domain.service.FuelTerminalService;
import ir.niopdc.fms.facade.FuelTerminalFacadeService;
import ir.niopdc.fms.validators.FuelTerminalValidator;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
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
    public String showListFuelTerminal(Model theModel) {
        // get the fuelTerminals from DB
        List<FuelTerminalGetDTO> theFuelTerminals = fuelTerminalService.findAllFuelTerminal();

        // add to the spring model
        theModel.addAttribute("fuelTerminals", theFuelTerminals);

        return "fuelTerminals/fuelTerminal-list";
    }

    @GetMapping("/add")
    public String showFormForAdd(@Nullable @RequestParam("fuelStationId") String fuelStationId, Model theModel) {

        // create model attribute to bind form data
        FuelTerminalCreateUpdateDTO theFuelTerminal = new FuelTerminalCreateUpdateDTO();

        theModel.addAttribute("fuelTerminal", theFuelTerminal);

        theModel.addAttribute("theFuelStationId", fuelStationId);

        // add path variable to model for specify witch page should show after creating a new obj
        if (fuelStationId != null) {
            theModel.addAttribute("path",
                    URI.create("fuelStations/info?fuelStationId=" + fuelStationId));
        }

        return "fuelTerminals/fuelTerminal-create";
    }

    @PostMapping("/save")
    public String createFuelTerminal(
            @Valid @ModelAttribute("fuelTerminal") FuelTerminalCreateUpdateDTO theFuelTerminal,
            BindingResult result,
            @Nullable @RequestParam("path") String path,
            Model theModel
            )
    {
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

        assert path != null;
        if (!path.isEmpty()) {
            return "redirect:/%s".formatted(path);
        }

        // use a redirect to prevent duplicate submissions
        return "redirect:/fuelTerminals/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("fuelTerminalId")  String theId) {

        fuelTerminalService.deleteFuelTerminal(theId);

        return "redirect:/fuelTerminals/list";
    }

    @GetMapping("/update")
    public String showFormForUpdate(
            @RequestParam("fuelTerminalId") String theId,
            Model theModel
    ) {
        // get the fuel station from svc
        FuelTerminalGetDTO theFuelTerminal = fuelTerminalService.findFuelTerminalById(theId);

        // set fuel terminal in model to populate the form
        theModel.addAttribute("fuelTerminal", theFuelTerminal);

        // send over to our form
        return "fuelTerminals/fuelTerminal-update";
    }

    @PostMapping("/update")
    public String updateFuelTerminal(
            @Valid @ModelAttribute("fuelTerminal") FuelTerminalCreateUpdateDTO theFuelTerminal,
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
        return "redirect:/fuelTerminals/list";
    }

    @GetMapping("/info")
    public String showInfoPage(@RequestParam("fuelTerminalId") String theId, Model theModel) {
        // get the fuel terminal from svc
        FuelTerminalGetDTO theFuelTerminal = fuelTerminalService.findFuelTerminalById(theId);

        // set fuel terminal in model to provide a ctx
        theModel.addAttribute("fuelTerminal", theFuelTerminal);

        // send over to our form
        return "fuelTerminals/fuelTerminal-info";
    }


}
