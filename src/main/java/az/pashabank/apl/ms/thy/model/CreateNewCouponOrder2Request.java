package az.pashabank.apl.ms.thy.model;

import az.pashabank.apl.ms.thy.constants.CouponType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.List;

public class CreateNewCouponOrder2Request {

    private Integer appId;
    private CouponType couponType;
    private String branchCode;
    @JsonIgnore
    private String branchName;
    private List<CardProductRequest> cardProductRequests;
    @JsonIgnore
    private BigDecimal amountToPay;
    private String shippingAddress;

    public CreateNewCouponOrder2Request() {
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public CouponType getCouponType() {
        return couponType;
    }

    public void setCouponType(CouponType couponType) {
        this.couponType = couponType;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public List<CardProductRequest> getCardProductRequests() {
        return cardProductRequests;
    }

    public void setCardProductRequests(List<CardProductRequest> cardProductRequests) {
        this.cardProductRequests = cardProductRequests;
    }

    public BigDecimal getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(BigDecimal amountToPay) {
        this.amountToPay = amountToPay;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @Override
    public String toString() {
        return "CreateNewCouponOrder2Request{" +
                "appId=" + appId +
                ", couponType=" + couponType +
                ", branchCode='" + branchCode + '\'' +
                ", branchName='" + branchName + '\'' +
                ", cardProductRequests=" + cardProductRequests +
                ", amountToPay=" + amountToPay +
                ", shippingAddress='" + shippingAddress + '\'' +
                '}';
    }
}
