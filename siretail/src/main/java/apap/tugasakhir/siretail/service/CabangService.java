package apap.tugasakhir.siretail.service;

import apap.tugasakhir.siretail.model.CabangModel;

public interface CabangService {
    void addCabang(CabangModel cabang);
    CabangModel updateCabang(CabangModel cabang);
    CabangModel getCabangById(Integer id);
}
