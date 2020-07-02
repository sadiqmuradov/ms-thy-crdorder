package az.pashabank.apl.ms.thy.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.StringJoiner;

public class ElectronCardProductView extends CardProductView {

    private List<String> couponCodes;

    public ElectronCardProductView() { }

    public ElectronCardProductView(Integer cardProductId, String cardProductName, Integer cardCount, BigDecimal totalAmount, List<String> couponCodes) {
        super(cardProductId, cardProductName, cardCount, totalAmount);
        this.couponCodes = couponCodes;
    }

    public ElectronCardProductView(CardProductView cardProductView) {
        super(cardProductView.getCardProductId(), cardProductView.getCardProductName(), cardProductView.getCardCount(), cardProductView.getTotalAmount());
    }

    public List<String> getCouponCodes() {
        return couponCodes;
    }

    public void setCouponCodes(List<String> couponCodes) {
        this.couponCodes = couponCodes;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ElectronCardProductView.class.getSimpleName() + "[", "]")
                .add("couponCodes=" + couponCodes)
                .add("cardProductId=" + cardProductId)
                .add("cardProductName='" + cardProductName + "'")
                .add("cardCount=" + cardCount)
                .add("totalAmount=" + totalAmount)
                .toString();
    }

}
