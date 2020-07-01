package az.pashabank.apl.ms.thy.model.thy;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckTkRequest {

    @JsonProperty("getMemberDetailsDetail")
    private MemberDetails memberDetails;

    public CheckTkRequest() {
    }

    public CheckTkRequest(MemberDetails memberDetails) {
        this.memberDetails = memberDetails;
    }

    public MemberDetails getMemberDetails() {
        return memberDetails;
    }

    public void setMemberDetails(MemberDetails memberDetails) {
        this.memberDetails = memberDetails;
    }

    @Override
    public String toString() {
        return "CheckTkRequest { memberDetails=" + memberDetails + " }";
    }

}