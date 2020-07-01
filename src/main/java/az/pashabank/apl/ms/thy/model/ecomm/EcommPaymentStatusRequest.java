package az.pashabank.apl.ms.thy.model.ecomm;

public class EcommPaymentStatusRequest {

    private Integer clientId;
    private String transactionId;
    private String ipAddress;

    public EcommPaymentStatusRequest(Integer clientId, String transactionId, String ipAddress) {
        this.clientId = clientId;
        this.transactionId = transactionId;
        this.ipAddress = ipAddress;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

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
        return "EcommPaymentStatusRequest{" +
                "clientId=" + clientId +
                ", transactionId='" + transactionId + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                '}';
    }

}
