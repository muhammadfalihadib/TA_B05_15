package apap.tugasakhir.siretail.service;

import apap.tugasakhir.siretail.model.ItemCabangModel;
import apap.tugasakhir.siretail.repository.ItemCabangDb;
import apap.tugasakhir.siretail.rest.ResultDetail;
import apap.tugasakhir.siretail.rest.Setting;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class ItemCabangRestServiceImpl implements ItemCabangRestService {
    @Autowired
    ItemCabangDb itemCabangDb;
    private final WebClient webClient;

    @Override
    public ItemCabangModel createItemCabang(ItemCabangModel itemCabang){
        return itemCabangDb.save(itemCabang);
    }

    @Override
    public List<ItemCabangModel> retrieveListItemCabang() {
        return itemCabangDb.findAll();
    }

    @Override
    public ItemCabangModel getItemCabangById(Integer id){
        Optional<ItemCabangModel> itemCabang = itemCabangDb.findById(id);
        
        if (itemCabang.isPresent()){
            return itemCabang.get();
        }
        else{
            throw new NoSuchElementException();
        }
    }

    @Override
    public Mono<List<ItemCabangModel>> getAllItemCabang(){
        return this.webClient.get().uri("/api/item").retrieve().bodyToMono(new ParameterizedTypeReference<List<ItemCabangModel>>(){});
    }

    @Override
    public Mono<String> getStokItem(String uuid){
        return this.webClient.get().uri("/api/item/" + uuid).retrieve().bodyToMono(String.class);
    }

    public ItemCabangRestServiceImpl(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl(Setting.itemCabangUrl).build();
    }
}

