package az.pashabank.apl.ms.thy.model;

import java.math.BigDecimal;

public class ValidateCouponCodeResponse {
    private Integer couponId;
    private BigDecimal couponPrice;

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public BigDecimal getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(BigDecimal couponPrice) {
        this.couponPrice = couponPrice;
    }

    @Override
    public String toString() {
        return "ValidateCouponCodeResponse{" +
                "couponId=" + couponId +
                ", couponPrice=" + couponPrice +
                '}';
    }
}
