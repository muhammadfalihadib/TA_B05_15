package apap.tugasakhir.siretail.controller;

import apap.tugasakhir.siretail.model.ItemCabangModel;
import apap.tugasakhir.siretail.model.CabangModel;
import apap.tugasakhir.siretail.rest.CouponDetail;
import apap.tugasakhir.siretail.rest.ResultCouponDetail;
import apap.tugasakhir.siretail.rest.ResultDetail;
import apap.tugasakhir.siretail.service.ItemCabangService;
import apap.tugasakhir.siretail.service.CabangService;
import apap.tugasakhir.siretail.service.ItemCabangRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/item")
public class ItemCabangController {
    
    @Qualifier("itemCabangRestServiceImpl")
    @Autowired
    private ItemCabangRestService itemCabangRestService;

    @Qualifier("cabangServiceImpl")
    @Autowired
    private CabangService cabangService;

    List<ResultDetail> arrResult;

    @GetMapping("/add/{id}")
    public String addItemToCabang(@PathVariable Integer id, Model model){
        ItemCabangModel itemCabang = new ItemCabangModel();
        CabangModel cabang = cabangService.getCabangById(id);
        itemCabang.setCabang(cabang);
        arrResult = itemCabangRestService.getAllItemCabang().getResult();
        model.addAttribute("itemCabang", itemCabang);
        model.addAttribute("listItem", arrResult);
        return "form-add-item";
    }

    @PostMapping("/add")
    public String addItemSubmitPage(
        @ModelAttribute ItemCabangModel itemCabang,
        Model model
    ){
        Map<String, ResultDetail> mapItem = new HashMap<String, ResultDetail>();
        for (ResultDetail i: arrResult){
            mapItem.put(i.getUuid(), i);
        }
        if (itemCabang.getStok() > mapItem.get(itemCabang.getUuidItem()).getStok()){
            model.addAttribute("itemCabang", itemCabang);
            model.addAttribute("listItem", arrResult);
            model.addAttribute("error", "Stok tidak cukup");
            return "form-add-item";
        }
        else if (itemCabang.getStok() <= 0){
            model.addAttribute("itemCabang", itemCabang);
            model.addAttribute("listItem", arrResult);
            model.addAttribute("error", "Input stok harus lebih dari 0");
            return "form-add-item";
        }
        else{
            try {
                ItemCabangModel itemExist = itemCabangRestService.getItemCabangByUuid(itemCabang.getUuidItem());
                System.out.println("masuk udh ada");
                itemExist.setStok(itemCabang.getStok() + itemExist.getStok());
                itemCabangRestService.createItemCabang(itemExist);

                Integer stok = mapItem.get(itemCabang.getUuidItem()).getStok() - itemCabang.getStok();
                itemCabangRestService.updateStok(itemExist.getUuidItem(), stok);
                
                model.addAttribute("nama", itemExist.getNama());
                model.addAttribute("cabang", itemExist.getCabang());
                return "add-item";
            }
            catch (NoSuchElementException e){
                System.out.println("masuk belom ada");
                itemCabang.setNama(mapItem.get(itemCabang.getUuidItem()).getNama());
                itemCabang.setKategori(mapItem.get(itemCabang.getUuidItem()).getKategori());
                itemCabang.setHarga(mapItem.get(itemCabang.getUuidItem()).getHarga());
        
                itemCabangRestService.createItemCabang(itemCabang);

                Integer stok = mapItem.get(itemCabang.getUuidItem()).getStok() - itemCabang.getStok();
                itemCabangRestService.updateStok(itemCabang.getUuidItem(), stok);

                model.addAttribute("nama", itemCabang.getNama());
                model.addAttribute("cabang", itemCabang.getCabang());
                return "add-item";
            }
        }
    }

    @GetMapping(value = "/promo/{idCabang}/{id}")
    public String addPromoFormPage(@PathVariable("idCabang") String idCabang,
                                   @PathVariable("id") String id,
                                   Model model){
        List<ResultCouponDetail> listCoupon = itemCabangRestService.getAllPromo().getResult();
        model.addAttribute("idCabang", idCabang);
        model.addAttribute("id", id);
        model.addAttribute("listCoupon", listCoupon);
        return  "list-coupon";
    }

    @RequestMapping(value = "/promo")
    public String addPromo(@RequestParam("id") int id,
                           @RequestParam("idCabang") int idCabang,
                           @RequestParam("idPromo") int idPromo,
                           @RequestParam("discount") float discount,
                           Model model){
        ItemCabangModel item = itemCabangRestService.getItemCabangById(id);
        item.setIdPromo(idPromo);
        float harga = item.getHarga()*((100 - discount)/100);
        item.setHarga((int) harga);
        itemCabangRestService.updateItemCabang(item);

        return "redirect:/cabang/view/" + idCabang;
    }

}
