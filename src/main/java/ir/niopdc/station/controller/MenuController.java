package ir.niopdc.station.controller;

import ir.niopdc.domain.menu.Menu;
import ir.niopdc.domain.menu.MenuDTO;
import ir.niopdc.domain.menu.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/api/menus")
    public List<MenuDTO> getMenus() {
        return menuService.findAll().stream().map(menu -> {
            MenuDTO dto = new MenuDTO();
            dto.setName(menu.getName());
            return dto;
        }).toList();
    }

    @GetMapping("/showMenu")
    public String showMenu() {
        List<Menu> menuDTOS = menuService.findAll();
        return "template";
    }



}

