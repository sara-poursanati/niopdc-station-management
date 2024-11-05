package ir.niopdc.fms.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String showLoginForm(Model model){
        return "admin-panel/login";
    }

    @RequestMapping("/login-error")
    public String showLoginFormError(Model model){
        model.addAttribute("loginError", true);
        return "admin-panel/login";
    }

}
