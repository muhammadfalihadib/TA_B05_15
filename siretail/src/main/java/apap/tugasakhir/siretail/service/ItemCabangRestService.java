package apap.tugasakhir.siretail.service;

import apap.tugasakhir.siretail.model.ItemCabangModel;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ItemCabangRestService {
    ItemCabangModel createItemCabang(ItemCabangModel itemCabang);
    List<ItemCabangModel> retrieveListItemCabang();
    ItemCabangModel getItemCabangById(Integer id);
    Mono<List<ItemCabangModel>> getAllItemCabang();
    Mono<String> getStokItem(String uuid);
}
