package az.pashabank.apl.ms.thy.model;

import java.util.StringJoiner;

public class CheckPaymentStatusRequest {

    private String transactionId;
    private Integer appId;
    private String ipAddress;

    public CheckPaymentStatusRequest() { }

    public CheckPaymentStatusRequest(String transactionId, String ipAddress) {
        this.transactionId = transactionId;
        this.ipAddress = ipAddress;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CheckPaymentStatusRequest.class.getSimpleName() + "[", "]")
                .add("transactionId='" + transactionId + "'")
                .add("appId=" + appId)
                .add("ipAddress='" + ipAddress + "'")
                .toString();
    }

}
