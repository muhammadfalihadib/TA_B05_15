package apap.tugasakhir.siretail.controller;

import apap.tugasakhir.siretail.model.CabangModel;
import apap.tugasakhir.siretail.model.UserModel;
import apap.tugasakhir.siretail.service.CabangService;
import apap.tugasakhir.siretail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cabang")
public class CabangController {

    @Autowired
    CabangService cabangService;

    @Autowired
    private UserService userService;

    @GetMapping("/add")
    public String addCabangFormPage(Model model){
        CabangModel cabang = new CabangModel();
        model.addAttribute("cabang", cabang);
        return "form-add-cabang";
    }

    @PostMapping("/add")
    public String addCabangSubmitPage(@ModelAttribute CabangModel cabang, Model model) {
        cabang.setPenanggungJawab(findCurrUser());
        cabang.setStatus(2);
        cabangService.addCabang(cabang);
        model.addAttribute("cabang", cabang.getNama());
        return "add-cabang";
    }

    @GetMapping("/update/{id}")
    public String updateUserFormPage(@PathVariable Integer id, Model model) {
        CabangModel cabang = cabangService.getCabangById(id);
        model.addAttribute("cabang", cabang);
        return "form-update-cabang";
    }

    @PostMapping("/update")
    public String updateUserSubmitPage(@ModelAttribute CabangModel cabang, Model model) {
        model.addAttribute("cabang", cabang.getNama());
        return "update-cabang";
    }

    @GetMapping("/view")
    public String viewDetailAgensiPage(
        @RequestParam(value = "id", required = false) Integer id,
        Model model
    ){

        CabangModel cabang = cabangService.getCabangById(id);
        if (cabang == null || id == null){
            return "no-cabang";
        }
        model.addAttribute("cabang", cabang);
        return "view-cabang";

    }

    public UserModel findCurrUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        String username = user.getUsername();
        return userService.findByUsername(username);
    }

}
