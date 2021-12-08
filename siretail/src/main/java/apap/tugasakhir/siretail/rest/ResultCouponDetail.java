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
    @JsonProperty(value = "id_coupon")
    private Integer id;

    @JsonProperty(value = "coupon_name")
    private String name;

    @JsonProperty(value = "coupon_code")
    private String code;

    @JsonProperty(value = "discount_amount")
    private Float discountAmount;

    // prevent error
    @JsonProperty(value = "expiry_date")
    // @DateTimeFormat(pattern = "YYYY-MM-dd")
    private String expiryDate;
}
