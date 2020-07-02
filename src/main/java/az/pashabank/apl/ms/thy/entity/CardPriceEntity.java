package az.pashabank.apl.ms.thy.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "thy_card_price")
@IdClass(CardPriceId.class)
public class CardPriceEntity {

    @Id
    private int cardProductId;
    private int lcyAmount;
    private int fcyAmount;
    @Id
    private int period;

}
