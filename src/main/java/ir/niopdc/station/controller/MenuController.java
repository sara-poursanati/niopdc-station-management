package ir.niopdc.station.controller;

import ir.niopdc.domain.menu.Menu;
import ir.niopdc.domain.menu.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/all-menu")
    public List<Menu> getAllMenus() {
        return menuService.findAll();
    }

    @GetMapping("/menu-page")
    public String getMenuPage(Model model) {
        List<Menu> menus = menuService.findAll();
        model.addAttribute("menus", menus);
        return "template";
    }




}

