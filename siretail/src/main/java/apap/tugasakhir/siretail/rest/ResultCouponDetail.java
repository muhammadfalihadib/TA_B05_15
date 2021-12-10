package apap.tugasakhir.siretail.rest;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultCouponDetail {
    @JsonProperty(value = "idCoupon")
    private Integer id;

    @JsonProperty(value = "couponName")
    private String name;

    @JsonProperty(value = "couponCode")
    private String code;

    @JsonProperty(value = "discountAmount")
    private Float discountAmount;

    @JsonProperty(value = "expiryDate")
    @DateTimeFormat(pattern = "YYYY-MM-dd")
    private LocalDate expiryDate;
}
