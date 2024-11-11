package ir.niopdc.station.controller.mvc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class GenericController<T, ID> {

    protected abstract List<T> getAll();
    protected abstract T getById(ID id);
    protected abstract T save(T dto);
    protected abstract T update(ID id, T dto);
    protected abstract void delete(ID id);

    @GetMapping("/get-all")
    public String getAllEntities(Model model) {
        List<T> entities = getAll();
        model.addAttribute("entities", entities);
        return "entity-list";
    }

    @GetMapping("/create-form")
    public String createForm(Model model) {
        model.addAttribute("dto", createEmptyDto());
        return "create-entity";
    }

    @PostMapping("/create")
    public ResponseEntity<String> createEntity(@ModelAttribute T dto) {
        try {
            save(dto);
            return ResponseEntity.ok("با موفقیت ایجاد شد!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("خطایی رخ داده است!");
        }
    }

    @GetMapping("/edit-form")
    public String editForm(@RequestParam(name = "id") ID id, Model model) {
        T dto = getById(id);
        model.addAttribute("dto", dto);
        return "edit-entity";
    }

    @PostMapping("/edit")
    public ResponseEntity<String> updateEntity(@ModelAttribute T dto, @RequestParam("id") ID id) {
        try {
            update(id, dto);
            return ResponseEntity.ok("با موفقیت ویرایش شد!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("خطایی رخ داده است!");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteEntity(@RequestParam(name = "id") ID id) {
        try {
            delete(id);
            return ResponseEntity.ok("با موفقیت حذف شد!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("خطایی رخ داده است!");
        }
    }

    protected abstract T createEmptyDto();
}
