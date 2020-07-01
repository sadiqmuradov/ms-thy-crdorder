package az.pashabank.apl.ms.thy.model.thy;

public class MemberOperationsReturn {

    private MemberProfileData memberProfileData;

    public MemberOperationsReturn() {
    }

    public MemberOperationsReturn(MemberProfileData memberProfileData) {
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
        return "MemberOperationsReturn { memberProfileData=" + memberProfileData + " }";
    }

}
