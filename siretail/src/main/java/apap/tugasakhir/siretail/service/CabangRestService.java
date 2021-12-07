package apap.tugasakhir.siretail.service;

import apap.tugasakhir.siretail.model.CabangModel;

import java.util.List;

public interface CabangRestService {
    CabangModel saveCabang(CabangModel cabang);
    List<CabangModel> getAllCabang();
}
