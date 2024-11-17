package ir.niopdc.station.controller;

import ir.niopdc.domain.fuelstation.FuelStation;
import ir.niopdc.domain.fuelstation.FuelStationService;
import ir.niopdc.domain.mediagateway.MediaGateway;
import ir.niopdc.domain.mediagateway.MediaGatewayService;
import ir.niopdc.station.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/media-gateway")
@RequiredArgsConstructor
public class MediaGatewayController {

    private final MediaGatewayService mediaGatewayService;

    private final FuelStationService fuelStationService;

    @GetMapping("/list")
    public String getAllMediaGateways(Model model) {
        List<MediaGateway> mediaGateways = mediaGatewayService.findAll();
        model.addAttribute("mediaGateways", mediaGateways);
        return "media-gateway/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("mediaGateway", new MediaGateway());
        return "media-gateway/create-media-gateway";
    }

    @PostMapping("/create")
    public String createMediaGateway(@ModelAttribute("mediaGateway") MediaGateway mediaGateway, Model model) {
        mediaGatewayService.save(mediaGateway);
        model.addAttribute("message", "saved successfully!");
        return "redirect:/media-gateway/list";
    }

    @GetMapping("/delete")
    public String deleteMediaGateway(@RequestParam String serialNumber) {
        if (mediaGatewayService.existsById(serialNumber)) {
            mediaGatewayService.deleteById(serialNumber);
        } else {
            throw new NotFoundException("MediaGateway Not Found!");
        }
        return "redirect:/media-gateway/list";
    }

    @GetMapping("/add-fuel-station/{serialNumber}")
    public String showAddFuelStationPage(@PathVariable String serialNumber, Model model) {
        List<FuelStation> fuelStations = fuelStationService.findAll();
        model.addAttribute("serialNumber", serialNumber);
        model.addAttribute("fuelStations", fuelStations);
        return "media-gateway/add-fuel-station";
    }

    @PostMapping("/add-fuel-station")
    public String assignFuelStationToMediaGateway(@RequestParam String serialNumber, @RequestParam String fuelStationId) {
        MediaGateway mediaGateway = mediaGatewayService.findById(serialNumber);
        FuelStation fuelStation = fuelStationService.findById(fuelStationId);
        if (mediaGateway != null && fuelStation != null) {
            mediaGateway.setFuelStation(fuelStation);
            mediaGatewayService.save(mediaGateway);
        } else {
            throw new NotFoundException("MediaGateway or FuelStation Not Found!");
        }
        return "redirect:/media-gateway/list";
    }





}
