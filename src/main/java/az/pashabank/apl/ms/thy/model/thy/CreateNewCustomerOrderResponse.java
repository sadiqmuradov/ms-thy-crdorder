package az.pashabank.apl.ms.thy.model.thy;

import java.util.StringJoiner;

public class CreateNewCustomerOrderResponse {

    private Integer newAppId;
    private String clientHandlerUrl;
    private String transactionId;

    public CreateNewCustomerOrderResponse() {
    }

    public CreateNewCustomerOrderResponse(Integer newAppId) {
        this.newAppId = newAppId;
    }

    public CreateNewCustomerOrderResponse(Integer newAppId, String clientHandlerUrl, String transactionId) {
        this.newAppId = newAppId;
        this.clientHandlerUrl = clientHandlerUrl;
        this.transactionId = transactionId;
    }

    public Integer getNewAppId() {
        return newAppId;
    }

    public void setNewAppId(Integer newAppId) {
        this.newAppId = newAppId;
    }

    public String getClientHandlerUrl() {
        return clientHandlerUrl;
    }

    public void setClientHandlerUrl(String clientHandlerUrl) {
        this.clientHandlerUrl = clientHandlerUrl;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CreateNewCustomerOrderResponse.class.getSimpleName() + "[", "]")
                .add("newAppId=" + newAppId)
                .add("clientHandlerUrl='" + clientHandlerUrl + "'")
                .add("transactionId='" + transactionId + "'")
                .toString();
    }

}
