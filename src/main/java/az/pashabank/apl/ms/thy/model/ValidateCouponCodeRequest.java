package az.pashabank.apl.ms.thy.model;

public class ValidateCouponCodeRequest {
    private int cardProductCode;
    private String couponCode;

    public int getCardProductCode() {
        return cardProductCode;
    }

    public void setCardProductCode(int cardProductCode) {
        this.cardProductCode = cardProductCode;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    @Override
    public String toString() {
        return "ValidateCouponCodeRequest{" +
                "cardProductCode=" + cardProductCode +
                ", couponCode='" + couponCode + '\'' +
                '}';
    }
}
