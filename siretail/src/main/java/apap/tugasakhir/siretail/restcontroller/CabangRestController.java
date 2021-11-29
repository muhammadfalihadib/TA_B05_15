package apap.tugasakhir.siretail.restcontroller;

import apap.tugasakhir.siretail.model.CabangModel;
import apap.tugasakhir.siretail.service.CabangRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

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
}
