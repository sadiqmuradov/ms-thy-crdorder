package az.pashabank.apl.ms.thy.model.ecomm;

public class ECommRegisterPaymentResponse {
    private String code;
    private String message;
    private String transactionId;
    private String clientHandlerUrl;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getClientHandlerUrl() {
        return clientHandlerUrl;
    }

    public void setClientHandlerUrl(String clientHandlerUrl) {
        this.clientHandlerUrl = clientHandlerUrl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ECommRegisterPaymentResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", clientHandlerUrl='" + clientHandlerUrl + '\'' +
                '}';
    }
}
