package ir.niopdc.station.controller.mvc;


import ir.niopdc.domain.area.Area;
import ir.niopdc.domain.area.AreaService;
import ir.niopdc.domain.zone.Zone;
import ir.niopdc.domain.zone.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/page/areas")
public class AreaViewController {

    private final ZoneService zoneService;
    private final AreaService areaService;

    @GetMapping("/create-area")
    public String createAreaForm(@RequestParam("zoneId") Long zoneId, Model model) {
        Area area = new Area();
        model.addAttribute("area", area);
        model.addAttribute("zoneId", zoneId);
        return "area-form";
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createArea(@ModelAttribute Area area, @RequestParam("zoneId") Long zoneId) {
        Map<String, String> response = new HashMap<>();
        try {
            Zone zone = zoneService.findById(zoneId);
            area.setZone(zone);
            areaService.save(area);
            response.put("message", "ناحیه با موفقیت ایجاد شد!");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("message", "خطا در ایجاد ناحیه!");
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
            model.addAttribute("zoneCode", area.getZone().getCode());
        } else {
            model.addAttribute("zoneCode", "");
        }

        return "edit-area";
    }

        @PostMapping("/edit")
    public ResponseEntity<Map<String, Object>> updateArea(@ModelAttribute Area area) {
        Map<String, Object> response = new HashMap<>();
        try {
            areaService.update(area);
            response.put("message", "ناحیه با موفقیت ویرایش شد!");
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
        } catch (Exception e) {
            response.put("message", "خطا در ویرایش ناحیه!");
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteArea(@RequestParam(name = "areaId") Long id) {
        areaService.deleteById(id);
        return ResponseEntity.ok("ناحیه با موفقیت حذف شد!");
    }
}