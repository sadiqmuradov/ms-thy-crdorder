package az.pashabank.apl.ms.thy.model;

import java.util.StringJoiner;

public class CreateNewCouponOrderStep1Response {

    private Integer newAppId;

    public CreateNewCouponOrderStep1Response() {
    }

    public CreateNewCouponOrderStep1Response(Integer newAppId) {
        this.newAppId = newAppId;
    }

    public Integer getNewAppId() {
        return newAppId;
    }

    public void setNewAppId(Integer newAppId) {
        this.newAppId = newAppId;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CreateNewCouponOrderStep1Response.class.getSimpleName() + "[", "]")
                .add("newAppId=" + newAppId)
                .toString();
    }

}
