package az.pashabank.apl.ms.thy.model;

import java.util.List;
import java.util.StringJoiner;

public class CheckCouponPaymentStatusResponse {

    protected String message;
    protected String amount;
    protected List<CardProductView> cardProductViews;

    public CheckCouponPaymentStatusResponse() { }

    public CheckCouponPaymentStatusResponse(String message, String amount, List<CardProductView> cardProductViews) {
        this.message = message;
        this.amount = amount;
        this.cardProductViews = cardProductViews;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public List<CardProductView> getCardProductViews() {
        return cardProductViews;
    }

    public void setCardProductViews(List<CardProductView> cardProductViews) {
        this.cardProductViews = cardProductViews;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CheckCouponPaymentStatusResponse.class.getSimpleName() + "[", "]")
                .add("message='" + message + "'")
                .add("amount='" + amount + "'")
                .add("cardProductViews=" + cardProductViews)
                .toString();
    }

}
