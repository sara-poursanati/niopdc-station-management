package ir.niopdc.station.controller.mvc;


import ir.niopdc.domain.area.Area;
import ir.niopdc.domain.area.AreaService;
import ir.niopdc.domain.zone.Zone;
import ir.niopdc.domain.zone.ZoneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/page/areas")
public class AreaViewController {

    private final ZoneService zoneService;
    private final AreaService areaService;
    private final MessageSource messageSource;

    @GetMapping("/create-area")
    public String createAreaForm(@RequestParam("zoneId") Long zoneId, Model model) {
        Area area = new Area();
        model.addAttribute("area", area);
        model.addAttribute("zoneId", zoneId);
        return "area-form";
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createArea(@ModelAttribute Area area, @RequestParam("zoneId") Long zoneId, Locale locale) {
        Map<String, String> response = new HashMap<>();
        try {
            Zone zone = zoneService.findById(zoneId);
            area.setZone(zone);
            areaService.save(area);
            response.put("message", messageSource.getMessage("area_created_successfully", null, locale));
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("message", messageSource.getMessage("error_creating_area", null, locale));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/get-all-areas")
    public String getAllAreas(@RequestParam("zoneId") Long zoneId, Model model) {
        Zone zone = zoneService.findById(zoneId);
        List<Area> areas = zone.getAreas();

        model.addAttribute("areas", areas);
        model.addAttribute("zoneId", zoneId);
        model.addAttribute("zoneName", zone.getName());
        return "area-list";
    }

    @GetMapping("/edit-area")
    public String editAreaForm(@RequestParam("areaId") Long areaId, Model model) {
        Area area = areaService.findById(areaId);

        if (area.getZone() != null) {
            model.addAttribute("zoneId", area.getZone().getId());
            model.addAttribute("zoneCode", area.getZone().getCode());
        } else {
            model.addAttribute("zoneId", "");
            model.addAttribute("zoneCode", "");
        }
        model.addAttribute("area", area);
        return "edit-area";
    }

    @PostMapping("/edit")
    public ResponseEntity<Map<String, Object>> updateArea(@Valid @ModelAttribute Area area, BindingResult result,
                                                          @RequestParam("zoneId") Long zoneId, Locale locale) {
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            response.put("message", messageSource.getMessage("input_mismatch", null, locale));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(response);
        }

        try {
            Zone zone = zoneService.findById(zoneId);
            if (zone != null) {
                area.setZone(zone);
            }

            areaService.update(area);
            response.put("message", messageSource.getMessage("area_edited_successfully", null, locale));
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
        } catch (Exception e) {
            response.put("message", messageSource.getMessage("error_editing_area", null, locale));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(response);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteArea(@RequestParam(name = "areaId") Long id, Locale locale) {
        try {
            areaService.deleteById(id);
            return ResponseEntity.ok(messageSource.getMessage("area_deleted_successfully", null, locale));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageSource.getMessage("error_deleting_area", null, locale));
        }
    }
}