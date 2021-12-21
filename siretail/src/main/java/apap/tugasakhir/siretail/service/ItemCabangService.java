package apap.tugasakhir.siretail.service;

import apap.tugasakhir.siretail.model.ItemCabangModel;

import java.util.List;

public interface ItemCabangService {
    void addItemCabang(ItemCabangModel itemCabang);
    ItemCabangModel getItemCabangById(Integer id);
    List<ItemCabangModel> getListItemCabang();
    void deleteItemCabang(Integer id);
}
