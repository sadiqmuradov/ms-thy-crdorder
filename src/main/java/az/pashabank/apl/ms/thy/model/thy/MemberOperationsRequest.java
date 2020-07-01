package az.pashabank.apl.ms.thy.model.thy;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MemberOperationsRequest {

    @JsonProperty("memberOperationsDetail")
    private MemberOperations memberOperations;

    public MemberOperationsRequest() {
    }

    public MemberOperationsRequest(MemberOperations memberOperations) {
        this.memberOperations = memberOperations;
    }

    public MemberOperations getMemberOperations() {
        return memberOperations;
    }

    public void setMemberOperations(MemberOperations memberOperations) {
        this.memberOperations = memberOperations;
    }

    @Override
    public String toString() {
        return "MemberOperationsRequest { memberOperations=" + memberOperations + " }";
    }

}
