package apap.tugasakhir.siretail.restcontroller;

import apap.tugasakhir.siretail.model.ItemCabangModel;
import apap.tugasakhir.siretail.service.ItemCabangRestService;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/v2")
public class ItemCabangRestController {
    
    @Autowired
    private ItemCabangRestService itemCabangRestService;

    @GetMapping(value = "list-item")
    private Mono<List<ItemCabangModel>> getAllItemCabang(){
        return itemCabangRestService.getAllItemCabang();
    }
}