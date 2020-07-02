package az.pashabank.apl.ms.thy.model;

import java.util.StringJoiner;

public class CheckCouponPaymentStatusRequest {

    private String transactionId;
    private String ipAddress;

    public CheckCouponPaymentStatusRequest() { }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CheckCouponPaymentStatusRequest.class.getSimpleName() + "[", "]")
                .add("transactionId='" + transactionId + "'")
                .add("ipAddress='" + ipAddress + "'")
                .toString();
    }

}
