package az.pashabank.apl.ms.thy.model;

import java.math.BigDecimal;
import java.util.StringJoiner;

public class CouponCode {

    private int id;
    private int cardProductCode;
    private String couponCode;
    private int status;
    private Integer appId;
    private BigDecimal couponPrice;
    private String serialNo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public BigDecimal getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(BigDecimal couponPrice) {
        this.couponPrice = couponPrice;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CouponCode.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("cardProductCode=" + cardProductCode)
                .add("couponCode='" + couponCode + "'")
                .add("status=" + status)
                .add("appId=" + appId)
                .add("couponPrice=" + couponPrice)
                .add("serialNo='" + serialNo + "'")
                .toString();
    }
}
