package az.pashabank.apl.ms.thy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardPriceId implements Serializable {

    private int cardProductId;
    private int period;
}