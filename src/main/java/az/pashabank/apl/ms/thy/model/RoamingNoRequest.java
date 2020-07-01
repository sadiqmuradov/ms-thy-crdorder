package az.pashabank.apl.ms.thy.model;

import java.util.StringJoiner;

public class RoamingNoRequest {

    private String roamingNo;

    public RoamingNoRequest() { }

    public String getRoamingNo() {
        return roamingNo;
    }

    public void setRoamingNo(String roamingNo) {
        this.roamingNo = roamingNo;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RoamingNoRequest.class.getSimpleName() + "[", "]")
                .add("roamingNo='" + roamingNo + "'")
                .toString();
    }
}
