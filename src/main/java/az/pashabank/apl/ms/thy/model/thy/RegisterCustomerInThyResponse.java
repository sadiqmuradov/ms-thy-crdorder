package az.pashabank.apl.ms.thy.model.thy;

public class RegisterCustomerInThyResponse {

    private MemberProfileData memberProfileData;

    public RegisterCustomerInThyResponse() {
    }

    public RegisterCustomerInThyResponse(MemberProfileData memberProfileData) {
        this.memberProfileData = memberProfileData;
    }

    public MemberProfileData getMemberProfileData() {
        return memberProfileData;
    }

    public void setMemberProfileData(MemberProfileData memberProfileData) {
        this.memberProfileData = memberProfileData;
    }

    @Override
    public String toString() {
        return "RegisterCustomerInThyResponse { memberProfileData=" + memberProfileData + " }";
    }

}
