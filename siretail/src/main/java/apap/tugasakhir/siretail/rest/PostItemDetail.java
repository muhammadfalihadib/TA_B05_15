package apap.tugasakhir.siretail.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostItemDetail {

    @JsonProperty("idItem")
    private String idItem;

    @JsonProperty("idKategori")
    private int idKategori;

    @JsonProperty("tambahanStok")
    private int tambahanStok;

    @JsonProperty("idCabang")
    private int idCabang;

}

