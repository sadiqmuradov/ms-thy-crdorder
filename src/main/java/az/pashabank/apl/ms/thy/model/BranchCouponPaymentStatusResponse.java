package az.pashabank.apl.ms.thy.model;

import java.util.List;
import java.util.StringJoiner;

public class BranchCouponPaymentStatusResponse extends CheckCouponPaymentStatusResponse {

    private String branchName;

    public BranchCouponPaymentStatusResponse() { }

    public BranchCouponPaymentStatusResponse(String message, String amount, List<CardProductView> cardProductViews, String branchName) {
        super(message, amount, cardProductViews);
        this.branchName = branchName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BranchCouponPaymentStatusResponse.class.getSimpleName() + "[", "]")
                .add("branchName='" + branchName + "'")
                .add("message='" + message + "'")
                .add("amount='" + amount + "'")
                .add("cardProductViews=" + cardProductViews)
                .toString();
    }

}
