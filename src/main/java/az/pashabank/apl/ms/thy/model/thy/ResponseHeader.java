package az.pashabank.apl.ms.thy.model.thy;

import java.util.List;

public class ResponseHeader {

    private String statusCode;
    private List<Message> messages;
    private String clientTransactionId;
    private String serverTransactionId;

    public ResponseHeader() {
    }

    public ResponseHeader(String statusCode, List<Message> messages, String clientTransactionId, String serverTransactionId) {
        this.statusCode = statusCode;
        this.messages = messages;
        this.clientTransactionId = clientTransactionId;
        this.serverTransactionId = serverTransactionId;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getClientTransactionId() {
        return clientTransactionId;
    }

    public void setClientTransactionId(String clientTransactionId) {
        this.clientTransactionId = clientTransactionId;
    }

    public String getServerTransactionId() {
        return serverTransactionId;
    }

    public void setServerTransactionId(String serverTransactionId) {
        this.serverTransactionId = serverTransactionId;
    }

    @Override
    public String toString() {
        return "ResponseHeader { " +
                "statusCode=" + statusCode + ", " +
                "messages=" + messages + ", " +
                "clientTransactionId=" + clientTransactionId + ", " +
                "serverTransactionId=" + serverTransactionId + " " +
                "}";
    }

}
