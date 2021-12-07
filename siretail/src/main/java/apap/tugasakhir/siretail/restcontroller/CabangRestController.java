package apap.tugasakhir.siretail.restcontroller;

import apap.tugasakhir.siretail.model.CabangModel;
import apap.tugasakhir.siretail.service.CabangRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CabangRestController {

    @Autowired
    private CabangRestService cabangRestService;

    @PostMapping(value = "/cabang")
    private CabangModel createCabang(@Valid @RequestBody CabangModel cabang,
                                           BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
            );
        } else{
            cabang.setStatus(0);
            return cabangRestService.saveCabang(cabang);
        }
    }

    @GetMapping(value = "/cabang")
    private List<CabangModel> getAllCabang(){
        return cabangRestService.getAllCabang();
    }
}
