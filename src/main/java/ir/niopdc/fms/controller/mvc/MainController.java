package ir.niopdc.fms.controller.mvc;

import ir.niopdc.fms.entity.service.FuelStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private FuelStationService fuelStationService;

    @GetMapping("/dashboard")
    public String showDashboard(Model theModel)
    {
        var fuelStations = fuelStationService.findAllFuelStation();

        theModel.addAttribute("fuelStations", fuelStations);

        return "admin-panel/index";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "admin-panel/login";
    }

    @GetMapping("/register")
    public String showRegister() {
        return "admin-panel/register";
    }

    @GetMapping("/list")
    public String showList() {
        return "admin-panel/users-list";
    }

    @GetMapping("/test")
    public String test() {
        return "admin-panel/form-elements";
    }

}
