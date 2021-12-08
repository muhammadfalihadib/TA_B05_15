package apap.tugasakhir.siretail.service;

import apap.tugasakhir.siretail.model.ItemCabangModel;
import apap.tugasakhir.siretail.repository.ItemCabangDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.List;


@Service
@Transactional
public class ItemCabangServiceImpl implements ItemCabangService {

    @Autowired
    ItemCabangDb itemCabangDb;

    @Override
    public void addItemCabang(ItemCabangModel itemCabang) {
        itemCabangDb.save(itemCabang);
    }

    @Override
    public ItemCabangModel getItemCabangById(Integer id) {
        Optional<ItemCabangModel> itemCabang = itemCabangDb.findById(id);
        if(itemCabang.isPresent()) return itemCabang.get();
        else return null;
    }

    @Override
    public List<ItemCabangModel> getListItemCabang(){
        return itemCabangDb.findAll();
    }
}
