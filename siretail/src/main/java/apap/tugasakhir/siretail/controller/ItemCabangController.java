package apap.tugasakhir.siretail.controller;

import apap.tugasakhir.siretail.model.ItemCabangModel;
import apap.tugasakhir.siretail.model.CabangModel;
import apap.tugasakhir.siretail.model.ListResultDetail;
import apap.tugasakhir.siretail.model.UserModel;
import apap.tugasakhir.siretail.rest.CouponDetail;
import apap.tugasakhir.siretail.rest.ItemDetail;
import apap.tugasakhir.siretail.rest.ResultCouponDetail;
import apap.tugasakhir.siretail.rest.ResultDetail;
import apap.tugasakhir.siretail.service.ItemCabangService;
import apap.tugasakhir.siretail.service.CabangService;
import apap.tugasakhir.siretail.service.UserService;
import apap.tugasakhir.siretail.service.ItemCabangRestService;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.User;

import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/item")
public class ItemCabangController {
    
    @Qualifier("itemCabangRestServiceImpl")
    @Autowired
    private ItemCabangRestService itemCabangRestService;

    @Qualifier("cabangServiceImpl")
    @Autowired
    private CabangService cabangService;

    @Qualifier("itemCabangServiceImpl")
    @Autowired
    private ItemCabangService itemCabangService;

    @Qualifier("userServiceImpl")
    @Autowired
    private UserService userService;

    List<ResultDetail> arrResult;
    // List<ResultDetail> arrResultNew; 

    @GetMapping("/add/{cabangId}")
    public String addItemToCabang(@PathVariable Integer cabangId, Model model){
        //ItemCabangModel itemCabang = new ItemCabangModel();

        CabangModel cabang = cabangService.getCabangById(cabangId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        String username = user.getUsername();
        UserModel userModel = userService.findByUsername(username);
        
        System.out.println(userModel.getRole());
        if (cabang.getPenanggungJawab().getUsername().equals(userModel.getNama()) || userModel.getRole().getNama().equals("Kepala Retail")){
            arrResult = itemCabangRestService.getAllItemCabang().getResult();
            ListResultDetail listResultDetail = new ListResultDetail();
            listResultDetail.setResultDetailList(new ArrayList<>());
            listResultDetail.getResultDetailList().add(new ResultDetail());
            model.addAttribute("cabangId", cabangId);
            model.addAttribute("listItem", arrResult);
            model.addAttribute("listResultDetail",listResultDetail);
            return "form-add-item";
        }
        return "error/403";
    }

    @PostMapping("/add/{cabangId}")
    public String addItemSubmitPage(
        @ModelAttribute ListResultDetail listResultDetail,
        @PathVariable Integer cabangId,
        Model model
    ){
        arrResult = itemCabangRestService.getAllItemCabang().getResult();
        Map<String, ResultDetail> mapItem = new HashMap<String, ResultDetail>();
        for (ResultDetail i: arrResult){
            mapItem.put(i.getUuid(), i);
        }
        for (ResultDetail rd : listResultDetail.getResultDetailList()){
            System.out.println(listResultDetail.getResultDetailList());
            if (rd.getStok() > mapItem.get(rd.getUuid()).getStok()){
                model.addAttribute("error", "Stok tidak cukup");
                model.addAttribute("cabangId", cabangId);
                model.addAttribute("listItem", arrResult);
                model.addAttribute("listResultDetail",listResultDetail);
                return "form-add-item";
            } else if (rd.getStok() <= 0){
                model.addAttribute("error", "Input stok harus lebih dari 0");
                model.addAttribute("cabangId", cabangId);
                model.addAttribute("listItem", arrResult);
                model.addAttribute("listResultDetail",listResultDetail);
                return "form-add-item";
            }
        }
        for (ResultDetail rd : listResultDetail.getResultDetailList()){
            try {
                ItemCabangModel rdCheck = new ItemCabangModel();
                rdCheck.setUuidItem(rd.getUuid());
                rdCheck.setNama(rd.getNama());
                rdCheck.setStok(rd.getStok());
                rdCheck.setHarga(rd.getHarga());
                rdCheck.setKategori(rd.getKategori());
                CabangModel cabang = cabangService.getCabangById(cabangId);
                rdCheck.setCabang(cabang);
                ItemCabangModel rdExist = null;
                // for (ItemCabangModel itemCek : cabang.getListItemCabang()){
                //     if (rdCheck.getUuidItem().equals(itemCek.getUuidItem())) {
                //         rdExist = itemCek;
                //         break;
                //     }
                // }
                
                for (ItemCabangModel itemCek : itemCabangRestService.retrieveListItemCabang()){
                    if (rdCheck.getCabang().getId().equals(itemCek.getCabang().getId())
                        && rdCheck.getUuidItem().equals(itemCek.getUuidItem())){
                        rdExist = itemCek;
                        break;
                    }
                }
                System.out.println(rdExist);

                if (rdExist != null && rdExist.getCabang().getId().equals(cabangId)){
                    System.out.println("masuk udh ada pada cabang tersebut");
                    rdExist.setStok(rd.getStok() + rdExist.getStok());

                    itemCabangRestService.createItemCabang(rdExist);

                    Integer stok = mapItem.get(rd.getUuid()).getStok() - rd.getStok();
                    itemCabangRestService.updateStok(rdExist.getUuidItem(), stok);
    
                    model.addAttribute("nama", rdExist.getNama());
                    // model.addAttribute("cabang", rdExist.getCabang());
                    // return "add-item";
                }
                else{
                    System.out.println("masuk blm ada pada cabang tersebut");
                    rd.setNama(mapItem.get(rd.getUuid()).getNama());
                    rd.setKategori(mapItem.get(rd.getUuid()).getKategori());
                    rd.setHarga(mapItem.get(rd.getUuid()).getHarga());
                    
                    // Ini kenapa gapake rdCheck aja yah yg di atas? -Ori
                    ItemCabangModel rdExistModel = new ItemCabangModel();
                    rdExistModel.setUuidItem(rd.getUuid());
                    rdExistModel.setNama(rd.getNama());
                    rdExistModel.setStok(rd.getStok());
                    rdExistModel.setHarga(rd.getHarga());
                    rdExistModel.setKategori(rd.getKategori());
                    rdExistModel.setCabang(cabangService.getCabangById(cabangId));

                    itemCabangRestService.createItemCabang(rdExistModel);
    
                    Integer stok = mapItem.get(rd.getUuid()).getStok() - rd.getStok();
                    itemCabangRestService.updateStok(rd.getUuid(), stok);
    
                    model.addAttribute("nama", rd.getNama());
                // model.addAttribute("cabang", itemCabang.getCabang());
                // return "add-item";
                }
            }
            catch (NoSuchElementException e){
                System.out.println("masuk belom ada samsek di database");
                rd.setNama(mapItem.get(rd.getUuid()).getNama());
                rd.setKategori(mapItem.get(rd.getUuid()).getKategori());
                rd.setHarga(mapItem.get(rd.getUuid()).getHarga());
                
                ItemCabangModel rdExistModel = new ItemCabangModel();
                rdExistModel.setUuidItem(rd.getUuid());
                rdExistModel.setNama(rd.getNama());
                rdExistModel.setStok(rd.getStok());
                rdExistModel.setHarga(rd.getHarga());
                rdExistModel.setKategori(rd.getKategori());
                rdExistModel.setCabang(cabangService.getCabangById(cabangId));

                itemCabangRestService.createItemCabang(rdExistModel);
 
                Integer stok = mapItem.get(rd.getUuid()).getStok() - rd.getStok();
                itemCabangRestService.updateStok(rd.getUuid(), stok);
 
                model.addAttribute("nama", rd.getNama());
                // model.addAttribute("cabang", itemCabang.getCabang());
                // return "add-item";
            }
        }
        return "add-item";
    }

    @PostMapping(value = "/add/{cabangId}", params="addRow")
    public String addItemOnCabang(
            @PathVariable Integer cabangId,
            @ModelAttribute ListResultDetail listResultDetail,
            Model model
    ){
//        System.out.println(listResultDetail.getResultDetailList().get(0).getNama());
//        System.out.println(listResultDetail.getResultDetailList().get(0).getUuid());
        if (listResultDetail.getResultDetailList() == null || listResultDetail.getResultDetailList().size() == 0) {
            listResultDetail.setResultDetailList(new ArrayList<>());
        }
        listResultDetail.getResultDetailList().add(new ResultDetail());
        arrResult = itemCabangRestService.getAllItemCabang().getResult();
        model.addAttribute("listResultDetail",listResultDetail);
        model.addAttribute("cabangId", cabangId);
        model.addAttribute("listItem", arrResult);
        return "form-add-item";
    }

    @PostMapping(value = "/add/{cabangId}", params="deleteRow")
    public String deleteItemOnCabang(
            @PathVariable Integer cabangId,
            @ModelAttribute ListResultDetail listResultDetail,
            @RequestParam("deleteRow") int row,
            Model model
    ){
        List<ResultDetail> temp = listResultDetail.getResultDetailList();
        listResultDetail.setResultDetailList(temp);
        listResultDetail.getResultDetailList().remove(row);
        arrResult = itemCabangRestService.getAllItemCabang().getResult();
        model.addAttribute("listResultDetail",listResultDetail);
        model.addAttribute("cabangId", cabangId);
        model.addAttribute("listItem", arrResult);
        return "form-add-item";
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