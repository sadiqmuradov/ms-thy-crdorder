package az.pashabank.apl.ms.thy.model;

import java.math.BigDecimal;
import java.util.StringJoiner;

public class CardProductView {

    protected Integer cardProductId;
    protected String cardProductName;
    protected Integer cardCount;
    protected BigDecimal totalAmount;

    public CardProductView() { }

    public CardProductView(Integer cardProductId, String cardProductName, Integer cardCount, BigDecimal totalAmount) {
        this.cardProductId = cardProductId;
        this.cardProductName = cardProductName;
        this.cardCount = cardCount;
        this.totalAmount = totalAmount;
    }

    public Integer getCardProductId() {
        return cardProductId;
    }

    public void setCardProductId(Integer cardProductId) {
        this.cardProductId = cardProductId;
    }

    public String getCardProductName() {
        return cardProductName;
    }

    public void setCardProductName(String cardProductName) {
        this.cardProductName = cardProductName;
    }

    public Integer getCardCount() {
        return cardCount;
    }

    public void setCardCount(Integer cardCount) {
        this.cardCount = cardCount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CardProductView.class.getSimpleName() + "[", "]")
                .add("cardProductId=" + cardProductId)
                .add("cardProductName='" + cardProductName + "'")
                .add("cardCount=" + cardCount)
                .add("totalAmount=" + totalAmount)
                .toString();
    }
}
