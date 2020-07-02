package az.pashabank.apl.ms.thy.model.thy;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.logstash.logback.encoder.org.apache.commons.lang.builder.ToStringBuilder;

public class SecurityQuestionsRequest {

    @JsonProperty("getSpecificComboBoxDetail")
    private SpecificComboBox specificComboBox;
    private SecurityQuestionsRequestHeader requestHeader;

    public SecurityQuestionsRequest() {
    }

    public SecurityQuestionsRequest(SpecificComboBox specificComboBox, SecurityQuestionsRequestHeader requestHeader) {
        this.specificComboBox = specificComboBox;
        this.requestHeader = requestHeader;
    }

    public SpecificComboBox getSpecificComboBox() {
        return specificComboBox;
    }

    public void setSpecificComboBox(SpecificComboBox specificComboBox) {
        this.specificComboBox = specificComboBox;
    }

    public SecurityQuestionsRequestHeader getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(SecurityQuestionsRequestHeader requestHeader) {
        this.requestHeader = requestHeader;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("specificComboBox", specificComboBox)
                .append("requestHeader", requestHeader)
                .toString();
    }
}