package az.pashabank.apl.ms.thy.model;

import java.util.StringJoiner;

public class CardProductRequest {

    private Integer cardType;
    private Integer cardCount;

    public CardProductRequest() { }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public Integer getCardCount() {
        return cardCount;
    }

    public void setCardCount(Integer cardCount) {
        this.cardCount = cardCount;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CardProductRequest.class.getSimpleName() + "[", "]")
                .add("cardType=" + cardType)
                .add("cardCount=" + cardCount)
                .toString();
    }
}
