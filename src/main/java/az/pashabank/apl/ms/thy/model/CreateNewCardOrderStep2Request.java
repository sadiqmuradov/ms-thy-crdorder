package az.pashabank.apl.ms.thy.model;

import az.pashabank.apl.ms.thy.constants.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNewCardOrderStep2Request {

    private Integer appId;
    private Integer cardType;
    private Integer couponId;
    private PaymentMethod paymentMethod;
    private String branchCode;
    @JsonIgnore
    private String branchName;
    private boolean urgent;
    private Integer promoCodeId;
    @JsonIgnore
    private Integer amountToPay;

}
