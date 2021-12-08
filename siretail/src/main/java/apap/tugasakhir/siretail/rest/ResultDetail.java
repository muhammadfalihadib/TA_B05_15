package apap.tugasakhir.siretail.rest;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultDetail {

    @JsonProperty(value = "uuid")
    private String uuid;

    @JsonProperty(value = "nama")
    private String nama;

    @JsonProperty(value = "harga")
    private Integer harga;

    @JsonProperty(value = "stok")
    private Integer stok;

    @JsonProperty(value = "kategori")
    private String kategori;
}