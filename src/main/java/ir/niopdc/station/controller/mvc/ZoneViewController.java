package ir.niopdc.station.controller.mvc;


import ir.niopdc.domain.zone.Zone;
import ir.niopdc.domain.zone.ZoneService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/page/zones")
public class ZoneViewController {

    private final ZoneService zoneService;

    @GetMapping("/get-all-zones")
    public String getAllZones(Model model) {
        List<Zone> zones = zoneService.getAllZones();
        model.addAttribute("zones", zones);
        return "zone-list";
    }

    @GetMapping("/create-zone")
    public String createZoneForm(Model model) {
        model.addAttribute("zone", new Zone());
        return "create-zone";
    }

    @PostMapping("/create")
    public ResponseEntity<String> createZone(@ModelAttribute Zone zone) {
        try {
            zoneService.saveZone(zone);
            return ResponseEntity.ok("منطقه با موفقیت اضافه شد!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("خطا در اضافه کردن منطقه!");
        }
    }

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }

    @ModelAttribute
    public void preventCache(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
    }

    @GetMapping("/edit-zone")
    public String editZoneForm(@RequestParam(name = "code") String code, Model model) {
        Zone zone = zoneService.getZoneByCode(code);
        model.addAttribute("zone", zone);
        return "edit-zone";
    }

    @PostMapping("/edit")
    public ResponseEntity<Model> updateZone(@ModelAttribute Zone zone, Model model) {
        try {
            Zone updateZone = zoneService.updateZone(zone.getCode(), zone);
            model.addAttribute("zone", updateZone);
            model.addAttribute("message", "منطقه با موفقیت ویرایش شد!");
            return ResponseEntity.ok(model);
        } catch (Exception e) {
            model.addAttribute("message", "خطا در ویرایش منطقه!");
            return ResponseEntity.status(HttpStatus.OK).body(model);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteZone(@RequestParam(name = "code") String code) {
        try {
            zoneService.deleteZone(code);
            return ResponseEntity.ok("منطقه با موفقیت حذف شد!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("خطا در حذف منطقه!");
        }
    }
}