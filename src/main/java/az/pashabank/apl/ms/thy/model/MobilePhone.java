package az.pashabank.apl.ms.thy.model;

public class MobilePhone {

    private String phoneCountryCode;
    private String phoneNumber;

    public MobilePhone() {
    }

    public MobilePhone(String phoneCountryCode, String phoneNumber) {
        this.phoneCountryCode = phoneCountryCode;
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneCountryCode() {
        return phoneCountryCode;
    }

    public void setPhoneCountryCode(String phoneCountryCode) {
        this.phoneCountryCode = phoneCountryCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "MobilePhone { " +
                "phoneCountryCode=" + phoneCountryCode + ", " +
                "phoneNumber=" + phoneNumber + " " +
                "}";
    }

}
