package az.pashabank.apl.ms.thy.model;

public class SendOtpRequest {

    private String pin;
    private String mobileNo;
    private String lang;

    public SendOtpRequest() {
    }

    public SendOtpRequest(String pin, String mobileNo, String lang) {
        this.pin = pin;
        this.mobileNo = mobileNo;
        this.lang = lang;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public String toString() {
        return "SendOtpRequest { "
                + "pin=" + pin + ", "
                + "mobileNo=" + mobileNo + ", "
                + "lang=" + lang + " }";
    }

}