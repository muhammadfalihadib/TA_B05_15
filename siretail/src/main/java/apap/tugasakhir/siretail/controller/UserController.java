package apap.tugasakhir.siretail.controller;

import apap.tugasakhir.siretail.model.RoleModel;
import apap.tugasakhir.siretail.model.UserModel;
import apap.tugasakhir.siretail.service.RoleService;
import apap.tugasakhir.siretail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<String> errorMessages = userService.isValidPassword(user.getPassword());
        if (errorMessages.size() > 0) {
            model.addAttribute("errorMessages", errorMessages);
            model.addAttribute("user", user);
            List<RoleModel> listRole = roleService.getListRole();
            model.addAttribute("listRole", listRole);
            return "form-add-user";
        } else if (!userService.isValidUsername(user.getUsername())) {
            model.addAttribute("usernameMessages", "Username sudah digunakan");
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
            @PathVariable Integer id,
            Model model
    ){
        UserModel user = userService.getUserById(id);
        if (user.getRole().getNama().equals("Kepala Retail") && userService.hasAuthority("Manager Cabang")) {
            return "error/403";
        }
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
        List<String> errorMessages = userService.isValidPassword(user.getPassword());
        if (errorMessages.size() > 0) {
            model.addAttribute("errorMessages", errorMessages);
            model.addAttribute("user", user);
            List<RoleModel> listRole = roleService.getListRole();
            model.addAttribute("listRole", listRole);
            return "form-update-user";
        } else if (!userService.isValidUsername(user.getUsername())) {
            model.addAttribute("usernameMessages", "Username sudah digunakan");
            model.addAttribute("user", user);
            List<RoleModel> listRole = roleService.getListRole();
            model.addAttribute("listRole", listRole);
            return "form-update-user";
        }
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
