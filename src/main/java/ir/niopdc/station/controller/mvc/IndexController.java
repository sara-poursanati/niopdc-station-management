package ir.niopdc.station.controller.mvc;

import ir.niopdc.domain.fuelstation.FuelStation;
import ir.niopdc.domain.fuelstation.FuelStationService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController {

    private FuelStationService fuelStationService;

    @Autowired
    private void setFuelStationService(FuelStationService fuelStationService) {
        this.fuelStationService = fuelStationService;
    }

    @RequestMapping("/")
    public String showDashboard(Model theModel, @Nullable @RequestParam String searchedWord)
    {

        if (searchedWord != null) {
            List<FuelStation> fuelStations = fuelStationService.findFuelStationBySearchText(searchedWord);

            theModel.addAttribute("fuelStations", fuelStations);

            return "admin-panel/result-search";
        }

        List<FuelStation> fuelStations = fuelStationService.findAll();

        theModel.addAttribute("fuelStations", fuelStations);

        return "admin-panel/index";
    }

}
