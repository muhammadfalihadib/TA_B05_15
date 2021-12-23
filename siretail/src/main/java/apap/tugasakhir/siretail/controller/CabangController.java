package apap.tugasakhir.siretail.controller;

import apap.tugasakhir.siretail.model.CabangModel;
import apap.tugasakhir.siretail.model.UserModel;
import apap.tugasakhir.siretail.model.ItemCabangModel;
import apap.tugasakhir.siretail.service.CabangService;
import apap.tugasakhir.siretail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Null;

@Controller
@RequestMapping("/cabang")
public class CabangController {

    @Autowired
    CabangService cabangService;

    @Autowired
    private UserService userService;

    public UserModel findCurrUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        String username = user.getUsername();
        return userService.findByUsername(username);
    }

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
        // agar bisa di add attribute di html according to its role
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        String username = user.getUsername();
        UserModel userModel = userService.findByUsername(username);

        CabangModel cabang = cabangService.getCabangById(id);
        model.addAttribute("cabang", cabang);
        model.addAttribute("user", userModel);
        return "form-update-cabang";
    }

    @PostMapping("/update")
    public String updateUserSubmitPage(@ModelAttribute CabangModel cabang, Model model) {
        cabangService.updateCabang(cabang);
        model.addAttribute("cabang", cabang.getNama());
        return "update-cabang";
    }

    @GetMapping("/view/{id}")
    public String viewDetailCabangPage(
        @PathVariable Integer id,
        Model model
    ){
        // agar bisa di add attribute di html according to its role
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        String username = user.getUsername();
        UserModel userModel = userService.findByUsername(username);

        CabangModel cabang = cabangService.getCabangById(id);
        if (cabang == null || id == null){
            return "no-cabang";
        }
        List<ItemCabangModel> listItemCabang = cabang.getListItemCabang();
        
        model.addAttribute("cabang", cabang);
        model.addAttribute("listItemCabang", listItemCabang);
        model.addAttribute("user", userModel);
        return "detail-cabang";
    }

    @GetMapping("/view")
    public String viewDetailCabangPageRequestParam(
        @RequestParam Integer idCabang,
        Model model
    ){

        CabangModel cabang = cabangService.getCabangById(idCabang);
        if (cabang == null || idCabang == null){
            return "no-cabang";
        }
        List<ItemCabangModel> listItemCabang = cabang.getListItemCabang();
        
        model.addAttribute("cabang", cabang);
        model.addAttribute("listItemCabang", listItemCabang);
        return "detail-cabang";
    }

    @GetMapping("/viewall")
    public String listCabang(Model model){
        // agar bisa di add attribute di html according to its role
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        String username = user.getUsername();
        UserModel userModel = userService.findByUsername(username);
        
        List<CabangModel> listCabang = cabangService.getListCabang();
        List<CabangModel> listCabangPovManager = new ArrayList<>();
        if (findCurrUser().getRole().getNama().equals("Manager Cabang")){
            System.out.println("masuk role managercabang");
            for (CabangModel cabang: listCabang){
                if (cabang.getPenanggungJawab() == null){
                    continue;
                }
                String penanggungJawab = cabang.getPenanggungJawab().getNama();
                if (penanggungJawab.equals(findCurrUser().getNama())){
                    listCabangPovManager.add(cabang);
                    System.out.println("masuk view all yang ada cabangnya");
                    model.addAttribute("listCabangPovManager", listCabangPovManager);
                    System.out.println(listCabangPovManager);
                }
                else{
                    System.out.println("masuk view all yang ga ada cabangnya");
                }
            }
            System.out.println(listCabangPovManager.size());
            return "viewall-cabang-pov-manager";
            
        }
        model.addAttribute("user", userModel);
        model.addAttribute("listCabang", listCabang);
        return "viewall-cabang";
    }

    @GetMapping("delete/{id}")
    public String deleteCabang(
            Model model,
            @PathVariable Integer id
    ){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        String username = user.getUsername();
        UserModel userModel = userService.findByUsername(username);

        CabangModel cabang = cabangService.getCabangById(id);
        model.addAttribute("cabang", cabang);
        model.addAttribute("user", userModel);
        List<ItemCabangModel> listItem = cabang.getListItemCabang();
        if (listItem.size() > 0){
            return "delete-cabang-fail";
        }
        cabangService.deleteCabang(id);
        return "delete-cabang";
    }

    @GetMapping("/request")
    public String listCabangRequest(Model model){
        // agar bisa di add attribute di html according to its role
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        String username = user.getUsername();
        UserModel userModel = userService.findByUsername(username);

        List<CabangModel> listCabang = cabangService.getListCabangRequest();
        model.addAttribute("listCabang", listCabang);
        model.addAttribute("user", userModel);
        return "viewall-permintaan-cabang";
    }

    @PostMapping(value = "/request/{id}", params = "accept")
    public String acceptCabangRequest(
            @PathVariable Integer id,
            Model model)
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserModel penanggungJawab = userService.findByUsername(username);
        CabangModel cabang = cabangService.getCabangById(id);
        cabang.setPenanggungJawab(penanggungJawab);
        cabang.setStatus(2);
        CabangModel cabangAccepted = cabangService.updateCabang(cabang);
        model.addAttribute("cabang", cabangAccepted);
        model.addAttribute("accepted", true);
        return "request-cabang";
    }

    @PostMapping(value = "/request/{id}", params = "reject")
    public String rejectCabangRequest(
            @PathVariable Integer id,
            Model model)
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserModel penanggungJawab = userService.findByUsername(username);
        CabangModel cabang = cabangService.getCabangById(id);
        cabang.setPenanggungJawab(penanggungJawab);
        cabang.setStatus(1);
        CabangModel cabangRejected = cabangService.updateCabang(cabang);
        model.addAttribute("cabang", cabangRejected);
        model.addAttribute("rejected", true);
        return "request-cabang";
    }
}
