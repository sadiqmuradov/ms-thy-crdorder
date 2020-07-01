package az.pashabank.apl.ms.thy.model;

import java.util.StringJoiner;

public class CheckOtpRequest {

    private String otp;
    private String pin;

    public CheckOtpRequest() {
    }

    public CheckOtpRequest(String otp, String pin) {
        this.otp = otp;
        this.pin = pin;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CheckOtpRequest.class.getSimpleName() + "[", "]")
                .add("otp='" + otp + "'")
                .add("pin='" + pin + "'")
                .toString();
    }

}