package az.pashabank.apl.ms.thy.model;

import az.pashabank.apl.ms.thy.entity.PromoCodeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPromoCodeResponse {

    private PromoCodeEntity promoCode;

}
