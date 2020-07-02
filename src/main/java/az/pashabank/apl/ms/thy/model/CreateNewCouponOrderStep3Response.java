package az.pashabank.apl.ms.thy.model;

import java.util.StringJoiner;

public class CreateNewCouponOrderStep3Response {

    private String clientHandlerUrl;
    private String transactionId;

    public CreateNewCouponOrderStep3Response() { }

    public CreateNewCouponOrderStep3Response(String clientHandlerUrl, String transactionId) {
        this.clientHandlerUrl = clientHandlerUrl;
        this.transactionId = transactionId;
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
        return new StringJoiner(", ", CreateNewCouponOrderStep3Response.class.getSimpleName() + "[", "]")
                .add("clientHandlerUrl='" + clientHandlerUrl + "'")
                .add("transactionId='" + transactionId + "'")
                .toString();
    }

}
