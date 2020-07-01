package az.pashabank.apl.ms.thy.model.thy;

public class Message {

    private String localizedDescription;
    private String messageType;
    private String messageCode;

    public Message() {
    }

    public Message(String localizedDescription, String messageType, String messageCode) {
        this.localizedDescription = localizedDescription;
        this.messageType = messageType;
        this.messageCode = messageCode;
    }

    public String getLocalizedDescription() {
        return localizedDescription;
    }

    public void setLocalizedDescription(String localizedDescription) {
        this.localizedDescription = localizedDescription;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String toString() {
        return "Message { " +
                "localizedDescription=" + localizedDescription + ", " +
                "messageType=" + messageType + ", " +
                "messageCode=" + messageCode + " " +
                "}";
    }

}
