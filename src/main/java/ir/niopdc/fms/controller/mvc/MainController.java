package ir.niopdc.fms.controller.mvc;

import ir.niopdc.fms.domain.service.FuelStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    private FuelStationService fuelStationService;

    @Autowired
    private void setFuelStationService(FuelStationService fuelStationService) {
        this.fuelStationService = fuelStationService;
    }

    @RequestMapping("/")
    public String showDashboard(Model theModel)
    {
        var fuelStations = fuelStationService.findAllFuelStation();

        theModel.addAttribute("fuelStations", fuelStations);

        return "admin-panel/index";
    }


}
