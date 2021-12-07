package apap.tugasakhir.siretail.service;


import apap.tugasakhir.siretail.model.CabangModel;
import apap.tugasakhir.siretail.repository.CabangDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CabangRestServiceImpl implements CabangRestService {

    @Autowired
    CabangDb cabangDb;

    @Override
    public CabangModel saveCabang(CabangModel cabang){
        return cabangDb.save(cabang);
    };

    @Override
    public List<CabangModel> getAllCabang(){
        return cabangDb.findAll();
    };
}
