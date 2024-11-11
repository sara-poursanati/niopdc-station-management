package ir.niopdc.station.controller.mvc.area;


import ir.niopdc.domain.area.AreaService;
import ir.niopdc.domain.area.dto.AreaDto;
import ir.niopdc.station.controller.mvc.GenericController;
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
@RequestMapping("/page/areas")
public class AreaViewController extends GenericController<AreaDto, String> {

    private final AreaService areaServiceImpl;

    @Override
    @GetMapping("/get-all-areas")
    public String getAllEntities(Model model) {
        List<AreaDto> areas = areaServiceImpl.getAllAreas();
        model.addAttribute("areas", areas);
        return "area-list";
    }

    @GetMapping("/create-area")
    public String createAreaForm(Model model) {
        model.addAttribute("areaDto", new AreaDto());
        return "create-area";
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

    @GetMapping("/edit-area")
    public String editAreaForm(@RequestParam(name = "code") String code, Model model) {
        AreaDto areaDto = areaServiceImpl.getAreaByCode(code);
        model.addAttribute("areaDto", areaDto);
        return "edit-area";
    }

    // Remove this method to rely on the generic controller's `update`
    /*
    @PostMapping("/edit")
    public ResponseEntity<Model> updateArea(@ModelAttribute AreaDto areaDto, Model model) {
        try {
            AreaDto dto = areaServiceImpl.updateArea(areaDto.getCode(), areaDto);
            model.addAttribute("areaDto", dto);
            model.addAttribute("message", "منطقه با موفقیت ویرایش شد!");
            return ResponseEntity.ok(model);
        } catch (Exception e) {
            model.addAttribute("message", "خطا در ویرایش منطقه!");
            return ResponseEntity.status(HttpStatus.OK).body(model);
        }
    }
    */

    @DeleteMapping("/delete-area")
    public ResponseEntity<String> deleteArea(@RequestParam(name = "code") String code) {
        try {
            areaServiceImpl.deleteArea(code);
            return ResponseEntity.ok("منطقه با موفقیت حذف شد!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("خطا در حذف منطقه!");
        }
    }

    @Override
    @PostMapping("/edit")
    public ResponseEntity<String> updateEntity(@ModelAttribute AreaDto areaDto, @RequestParam("id") String code) {
        try {
            areaServiceImpl.updateArea(code, areaDto);
            return ResponseEntity.ok("منطقه با موفقیت ویرایش شد!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("خطایی رخ داده است!");
        }
    }

    @Override
    protected List<AreaDto> getAll() {
        return areaServiceImpl.getAllAreas();
    }

    @Override
    protected AreaDto getById(String code) {
        return areaServiceImpl.getAreaByCode(code);
    }

    @Override
    protected AreaDto save(AreaDto areaDto) {
        return areaServiceImpl.saveArea(areaDto);
    }

    @Override
    protected AreaDto update(String code, AreaDto areaDto) {
        return areaServiceImpl.updateArea(code, areaDto);
    }

    @Override
    protected void delete(String code) {
        areaServiceImpl.deleteArea(code);
    }

    @Override
    protected AreaDto createEmptyDto() {
        return new AreaDto();
    }
}