package apap.tugasakhir.siretail.service;

import apap.tugasakhir.siretail.model.ItemCabangModel;
import apap.tugasakhir.siretail.repository.ItemCabangDb;
import apap.tugasakhir.siretail.rest.CouponDetail;
import apap.tugasakhir.siretail.rest.ItemDetail;
import apap.tugasakhir.siretail.rest.ResultDetail;
import apap.tugasakhir.siretail.rest.Setting;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class ItemCabangRestServiceImpl implements ItemCabangRestService {
    @Autowired
    ItemCabangDb itemCabangDb;
    private final WebClient webClient;
    private final WebClient webClientCoupon;

    @Override
    public ItemCabangModel createItemCabang(ItemCabangModel itemCabang){
        return itemCabangDb.save(itemCabang);
    }

    @Override
    public List<ItemCabangModel> retrieveListItemCabang() {
        return itemCabangDb.findAll();
    }

    @Override
    public ItemCabangModel getItemCabangByUuid(String uuidItem){
        Optional<ItemCabangModel> itemCabang = itemCabangDb.findByUuidItem(uuidItem);
        if (itemCabang.isPresent()){
            return itemCabang.get();
        }
        else{
            throw new NoSuchElementException();
        }
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
    public ItemDetail getAllItemCabang(){
        return this.webClient.get().uri("/api/item")
        .retrieve()
        .bodyToMono(ItemDetail.class)
        .block();
        //.getResult();
    }

    @Override
    public Mono<String> getStokItem(String uuid){
        return this.webClient.get().uri("/api/item/" + uuid).retrieve().bodyToMono(String.class);
    }

    @Override
    public CouponDetail getAllPromo() {
        return this.webClientCoupon.get().uri("/rest/coupon").retrieve().bodyToMono(CouponDetail.class).block();
    }

    public ItemCabangModel updateItemCabang(ItemCabangModel itemCabang) {
        return itemCabangDb.save(itemCabang);
    }

    public ItemCabangRestServiceImpl(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl(Setting.itemCabangUrl).build();
        this.webClientCoupon = webClientBuilder.baseUrl(Setting.couponUrl).build();
    }
}

