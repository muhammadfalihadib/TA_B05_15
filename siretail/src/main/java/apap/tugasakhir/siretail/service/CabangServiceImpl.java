package apap.tugasakhir.siretail.service;

import apap.tugasakhir.siretail.model.CabangModel;
import apap.tugasakhir.siretail.repository.CabangDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.List;

@Service
@Transactional
public class CabangServiceImpl implements CabangService {

    @Autowired
    CabangDb cabangDb;

    @Override
    public void addCabang(CabangModel cabang) {
        cabangDb.save(cabang);
    }

    @Override
    public CabangModel updateCabang(CabangModel cabang) {
        return cabangDb.save(cabang);
    }

    @Override
    public CabangModel getCabangById(Integer id) {
        Optional<CabangModel> cabang = cabangDb.findById(id);
        if(cabang.isPresent()) return cabang.get();
        else return null;
    }

    @Override
    public List<CabangModel> getListCabang(){
        return cabangDb.findAll();
    }

    @Override
    public List<CabangModel> getListCabangRequest() {
        return cabangDb.findByStatus(0);
    }

    @Override
    public void deleteCabang(Integer id){
        cabangDb.deleteById(id);
    }
}
