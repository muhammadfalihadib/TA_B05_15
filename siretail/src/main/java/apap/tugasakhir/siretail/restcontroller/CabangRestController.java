package apap.tugasakhir.siretail.restcontroller;

import apap.tugasakhir.siretail.model.CabangModel;
import apap.tugasakhir.siretail.rest.BaseResponse;
import apap.tugasakhir.siretail.rest.CabangDetail;
import apap.tugasakhir.siretail.service.CabangRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CabangRestController {

    @Autowired
    private CabangRestService cabangRestService;

    @PostMapping(value = "/cabang")
    private BaseResponse<CabangModel> createCabang(@Valid @RequestBody CabangDetail cabang,
                                           BindingResult bindingResult){
        BaseResponse<CabangModel> response = new BaseResponse<>();
        if(bindingResult.hasFieldErrors()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
            );
        } else{
            try {
                CabangModel cabangModel = new CabangModel();
                cabangModel.setStatus(0);
                cabangModel.setNama(cabang.getNama());
                cabangModel.setAlamat(cabang.getAlamat());
                cabangModel.setUkuran(cabang.getUkuran());
                cabangModel.setNoTelp(cabang.getNoTelp());
                response.setStatus(200);
                response.setMessage("success");
                response.setResult(cabangRestService.saveCabang(cabangModel));
            } catch (Exception e){
                response.setStatus(400);
                response.setMessage("Request body has invalid type or missing field");
                response.setResult(null);
            }
            return response;
        }
    }

    @GetMapping(value = "/cabang")
    private BaseResponse<List<CabangModel>> getAllCabang(){
        BaseResponse<List<CabangModel>> response = new BaseResponse<>();
        response.setStatus(200);
        response.setMessage("success");
        response.setResult(cabangRestService.getAllCabang());
        return response;
    }
}
