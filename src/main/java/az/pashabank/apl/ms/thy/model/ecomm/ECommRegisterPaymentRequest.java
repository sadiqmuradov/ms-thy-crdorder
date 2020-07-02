package az.pashabank.apl.ms.thy.model.ecomm;

public class ECommRegisterPaymentRequest {

    private int clientId;
    private String amountInCents;
    private String currencyIsoName;
    private String ipAddress;
    private String description;
    private String language;

    public ECommRegisterPaymentRequest() { }

    public ECommRegisterPaymentRequest(int clientId, String amountInCents, String currencyIsoName, String ipAddress, String description, String language) {
        this.clientId = clientId;
        this.amountInCents = amountInCents;
        this.currencyIsoName = currencyIsoName;
        this.ipAddress = ipAddress;
        this.description = description;
        this.language = language;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getAmountInCents() {
        return amountInCents;
    }

    public void setAmountInCents(String amountInCents) {
        this.amountInCents = amountInCents;
    }

    public String getCurrencyIsoName() {
        return currencyIsoName;
    }

    public void setCurrencyIsoName(String currencyIsoName) {
        this.currencyIsoName = currencyIsoName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "RegisterPaymentRequest{" +
                "clientId=" + clientId +
                ", amountInCents='" + amountInCents + '\'' +
                ", currencyIsoName='" + currencyIsoName + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", description='" + description + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
