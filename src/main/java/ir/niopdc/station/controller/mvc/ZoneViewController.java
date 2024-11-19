package ir.niopdc.station.controller.mvc;


import ir.niopdc.domain.zone.Zone;
import ir.niopdc.domain.zone.ZoneService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
@RequestMapping("/page/zones")
public class ZoneViewController {

    private final ZoneService zoneService;
    private final MessageSource messageSource;

    @GetMapping("/get-all-zones")
    public String getAllZones(Model model) {
        List<Zone> zones = zoneService.findAll();
        model.addAttribute("zones", zones);
        return "zone-list";
    }

    @GetMapping("/create-zone")
    public String createZoneForm(Model model) {
        model.addAttribute("zone", new Zone());
        return "create-zone";
    }

    @PostMapping("/create")
    public ResponseEntity<String> createZone(@ModelAttribute Zone zone, Locale locale) {
        try {
            zoneService.save(zone);
            return ResponseEntity.ok(messageSource.getMessage("zone_created_successfully", null, locale));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageSource.getMessage("error_creating_zone", null, locale));
        }
    }

    @ModelAttribute
    public void preventCache(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
    }

    @GetMapping("/edit-zone")
    public String editZoneForm(@RequestParam("zoneId") Long zoneId, Model model) {
        Zone zone = zoneService.findById(zoneId);
        model.addAttribute("zone", zone);
        return "edit-zone";
    }

    @PostMapping("/edit")
    public ResponseEntity<Model> updateZone(@ModelAttribute Zone zone, Model model, Locale locale) {
        try {
            Zone existingZone = zoneService.findById(zone.getId());
            if (existingZone != null) {
                existingZone.setName(zone.getName());
                Zone updatedZone = zoneService.update(existingZone);
                model.addAttribute("zone", updatedZone);
                model.addAttribute("message", messageSource.getMessage("zone_edited_successfully", null, locale));
                return ResponseEntity.ok(model);
            } else {
                model.addAttribute("message", messageSource.getMessage("zone_not_found", null, locale));
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(model);
            }
        } catch (Exception e) {
            model.addAttribute("message", messageSource.getMessage("error_editing_zone", null, locale));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(model);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteZone(@RequestParam("zoneId") Long zoneId, Locale locale) {
        try {
            Zone zone = zoneService.findById(zoneId);
            if (zone.getAreas() != null && !zone.getAreas().isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(messageSource.getMessage("zone_has_related_areas", null, locale)); // Custom message for zones with areas
            }
            zoneService.deleteById(zoneId);
            return ResponseEntity.ok(messageSource.getMessage("zone_deleted_successfully", null, locale));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageSource.getMessage("error-deleting_zone", null, locale));
        }
    }
}