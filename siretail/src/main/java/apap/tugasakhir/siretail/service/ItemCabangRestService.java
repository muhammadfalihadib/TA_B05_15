package apap.tugasakhir.siretail.service;

import apap.tugasakhir.siretail.model.ItemCabangModel;
import apap.tugasakhir.siretail.rest.CouponDetail;
import apap.tugasakhir.siretail.rest.ItemDetail;
import apap.tugasakhir.siretail.rest.ResultDetail;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;

public interface ItemCabangRestService {
    ItemCabangModel createItemCabang(ItemCabangModel itemCabang);
    List<ItemCabangModel> retrieveListItemCabang();
    ItemCabangModel getItemCabangByUuid(String uuid);
    ItemCabangModel getItemCabangById(Integer id);
    ItemDetail getAllItemCabang();
    Mono<String> getStokItem(String uuid);
    CouponDetail getAllPromo();
    ItemCabangModel updateItemCabang(ItemCabangModel itemCabang);
    void updateStok(String uuid, Integer stok);
}
