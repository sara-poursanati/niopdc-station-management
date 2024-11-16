package ir.niopdc.station.controller.mvc.area;


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
    public String createAreaForm(@RequestParam("zoneCode") String zoneCode, Model model) {
        Area area = new Area();
        model.addAttribute("area", area);
        model.addAttribute("zoneCode", zoneCode);
        return "area-form";
    }

    @PostMapping("/create")
    public ResponseEntity<Model> createArea(@ModelAttribute Area area, @RequestParam("zoneCode") String zoneCode, Model model) {
        try {
            Area savedArea = areaService.saveArea(area, zoneCode);
            model.addAttribute("area", savedArea);
            model.addAttribute("message", "ناحیه با موفقیت ایجاد شد!");
            return ResponseEntity.ok(model);
        } catch (RuntimeException e) {
            model.addAttribute("message", "خطا در ایجاد ناحیه!");
            return ResponseEntity.status(HttpStatus.OK).body(model);
        }
    }


    @GetMapping("/get-all-areas")
    public String getAllAreas(@RequestParam("zoneCode") String code, Model model) {
        List<Area> areas = areaService.getAllAreas(code);
        Zone zone = zoneService.getZoneByCode(code);
        model.addAttribute("areas", areas);
        model.addAttribute("zoneCode", code);
        model.addAttribute("zoneName", zone.getName());
        return "area-list";
    }

    @GetMapping("/edit-area")
    public String editAreaForm(@RequestParam(name = "code") String code, Model model) {
        Area area = areaService.getAreaByCode(code);
        model.addAttribute("area", area);

        if (area.getZone() != null) {
            model.addAttribute("zoneCode", area.getZone().getCode());
        } else {
            model.addAttribute("zoneCode", "");
        }

        return "edit-area";
    }

    @PostMapping("/edit")
    public ResponseEntity<Map<String, Object>> updateArea(@ModelAttribute Area area, @RequestParam("zoneCode") String zoneCode) {
        Map<String, Object> response = new HashMap<>();
        try {
            areaService.updateArea(zoneCode, area);
            response.put("message", "ناحیه با موفقیت ویرایش شد!");
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
        } catch (Exception e) {
            response.put("message", "خطا در ویرایش ناحیه!");
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteArea(@RequestParam(name = "areaId") Long id) {
        areaService.deleteArea(id);
        return ResponseEntity.ok("ناحیه با موفقیت حذف شد!");
    }
}