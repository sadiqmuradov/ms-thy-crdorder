package az.pashabank.apl.ms.thy.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "thy_promocode")
public class PromoCodeEntity {

    @Id
    private Integer id;
    private String promoCode;
    private Integer mainProduct;
    private Integer subProduct;
    private Integer promoMiles;
    private Integer status;
    private LocalDateTime expiryDate;

}
