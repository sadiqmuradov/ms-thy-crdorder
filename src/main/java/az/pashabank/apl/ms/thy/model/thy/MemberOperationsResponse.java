package az.pashabank.apl.ms.thy.model.thy;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MemberOperationsResponse {

    private ResponseHeader responseHeader;
    @JsonProperty("return")
    private MemberOperationsReturn memberOperationsReturn;

    public MemberOperationsResponse() {
    }

    public MemberOperationsResponse(MemberOperationsReturn memberOperationsReturn) {
        this.memberOperationsReturn = memberOperationsReturn;
    }

    public MemberOperationsResponse(ResponseHeader responseHeader, MemberOperationsReturn memberOperationsReturn) {
        this.responseHeader = responseHeader;
        this.memberOperationsReturn = memberOperationsReturn;
    }

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    public MemberOperationsReturn getMemberOperationsReturn() {
        return memberOperationsReturn;
    }

    public void setMemberOperationsReturn(MemberOperationsReturn memberOperationsReturn) {
        this.memberOperationsReturn = memberOperationsReturn;
    }

    @Override
    public String toString() {
        return "MemberOperationsResponse { responseHeader=" + responseHeader + ", memberOperationsReturn=" + memberOperationsReturn + " }";
    }

}
