package az.pashabank.apl.ms.thy.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.List;

public class MobileNumbersByPin {

    private List<String> mobileNumbers;

    public MobileNumbersByPin() {
    }

    public MobileNumbersByPin(List<String> mobileNumbers) {
        this.mobileNumbers = mobileNumbers;
    }

    public List<String> getMobileNumbers() {
        return mobileNumbers;
    }

    public void setMobileNumbers(List<String> mobileNumbers) {
        this.mobileNumbers = mobileNumbers;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("mobileNumbers", mobileNumbers)
                .toString();
    }

}
