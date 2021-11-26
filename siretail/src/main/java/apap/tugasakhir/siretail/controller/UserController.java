package apap.tugasakhir.siretail.controller;

import apap.tugasakhir.siretail.model.RoleModel;
import apap.tugasakhir.siretail.model.UserModel;
import apap.tugasakhir.siretail.service.RoleService;
import apap.tugasakhir.siretail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/add")
    public String addUserFormPage(Model model) {
        UserModel user = new UserModel();
        List<RoleModel> listRole = roleService.getListRole();
        model.addAttribute("user", user);
        model.addAttribute("listRole", listRole);
        return "form-add-user";
    }

    @PostMapping(value = "/add")
    public String addUserSubmitPage(
            @ModelAttribute UserModel user,
            Model model
    ) {
        if (!userService.isValidPassword(user.getPassword())) {
            model.addAttribute("message", "Pengisian password tidak sesuai ketentuan.");
            model.addAttribute("user", user);
            List<RoleModel> listRole = roleService.getListRole();
            model.addAttribute("listRole", listRole);
            return "form-add-user";
        }
        userService.addUser(user);
        model.addAttribute("username", user.getUsername());
        return "add-user";
    }

    @GetMapping("/update/{id}")
    public String updateUserFormPage(
            @PathVariable Long id,
            Model model
    ){
        UserModel user = userService.getUserById(id);
        List<RoleModel> listRole = roleService.getListRole();
        model.addAttribute("user", user);
        model.addAttribute("listRole", listRole);
        return "form-update-user";
    }

    @PostMapping("/update")
    public String updateUserSubmitPage(
            @ModelAttribute UserModel user,
            Model model
    ){
        UserModel updatedUser = userService.updateUser(user);
        model.addAttribute("username", updatedUser.getUsername());
        return "update-user";
    }

    @GetMapping("/viewall")
    public String listUser(Model model) {
        List<UserModel> listUser = userService.getListUser();
        model.addAttribute("listUser", listUser);
        return "viewall-user";
    }
}
