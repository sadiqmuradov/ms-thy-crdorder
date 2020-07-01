package az.pashabank.apl.ms.thy.model;

import java.util.StringJoiner;

public class CheckPaymentStatusResponse {

    private String message;
    private String cardProductName;
    private String branchName;
    private String amount;

    public CheckPaymentStatusResponse() {
    }

    public CheckPaymentStatusResponse(String message, String cardProductName, String branchName, String amount) {
        this.message = message;
        this.cardProductName = cardProductName;
        this.branchName = branchName;
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCardProductName() {
        return cardProductName;
    }

    public void setCardProductName(String cardProductName) {
        this.cardProductName = cardProductName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CheckPaymentStatusResponse.class.getSimpleName() + "[", "]")
                .add("message='" + message + "'")
                .add("cardProductName='" + cardProductName + "'")
                .add("branchName='" + branchName + "'")
                .add("amount='" + amount + "'")
                .toString();
    }

}
