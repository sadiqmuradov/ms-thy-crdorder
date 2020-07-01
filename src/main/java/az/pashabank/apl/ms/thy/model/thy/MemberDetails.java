package az.pashabank.apl.ms.thy.model.thy;

public class MemberDetails {

    private String ffid;

    public MemberDetails() {
    }

    public MemberDetails(String ffid) {
        this.ffid = ffid;
    }

    public String getFfid() {
        return ffid;
    }

    public void setFfid(String ffid) {
        this.ffid = ffid;
    }

    @Override
    public String toString() {
        return "MemberDetails { ffid=" + ffid + " }";
    }

}
