package az.pashabank.apl.ms.thy.model.thy;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MemberOperations {

    private String chanceType;
    @JsonProperty("enrollmentChannelDetail")
    private EnrollmentChannel enrollmentChannel;
    private MemberProfileData memberProfileData;

    public MemberOperations() {
    }

    public MemberOperations(String chanceType, EnrollmentChannel enrollmentChannel, MemberProfileData memberProfileData) {
        this.chanceType = chanceType;
        this.enrollmentChannel = enrollmentChannel;
        this.memberProfileData = memberProfileData;
    }

    public String getChanceType() {
        return chanceType;
    }

    public void setChanceType(String chanceType) {
        this.chanceType = chanceType;
    }

    public EnrollmentChannel getEnrollmentChannel() {
        return enrollmentChannel;
    }

    public void setEnrollmentChannel(EnrollmentChannel enrollmentChannel) {
        this.enrollmentChannel = enrollmentChannel;
    }

    public MemberProfileData getMemberProfileData() {
        return memberProfileData;
    }

    public void setMemberProfileData(MemberProfileData memberProfileData) {
        this.memberProfileData = memberProfileData;
    }

    @Override
    public String toString() {
        return "MemberOperations { " +
                "chanceType=" + chanceType + ", " +
                "enrollmentChannel=" + enrollmentChannel + ", " +
                "memberProfileData=" + memberProfileData + " " +
                "}";
    }

}
