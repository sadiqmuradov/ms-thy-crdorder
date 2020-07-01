package az.pashabank.apl.ms.thy.model.thy;

import java.util.List;

public class CheckTkReturn {

    private List<MemberData> memberDataKVPair;

    public CheckTkReturn() {
    }

    public CheckTkReturn(List<MemberData> memberDataKVPair) {
        this.memberDataKVPair = memberDataKVPair;
    }

    public List<MemberData> getMemberDataKVPair() {
        return memberDataKVPair;
    }

    public void setMemberDataKVPair(List<MemberData> memberDataKVPair) {
        this.memberDataKVPair = memberDataKVPair;
    }

    @Override
    public String toString() {
        return "CheckTkReturn { memberDataKVPair=" + memberDataKVPair + " }";
    }

}
