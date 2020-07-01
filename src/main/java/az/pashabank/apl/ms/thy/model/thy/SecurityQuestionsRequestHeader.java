package az.pashabank.apl.ms.thy.model.thy;

import org.apache.commons.lang.builder.ToStringBuilder;

public class SecurityQuestionsRequestHeader {

    private String languageCode;

    public SecurityQuestionsRequestHeader() {
    }

    public SecurityQuestionsRequestHeader(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("languageCode", languageCode)
                .toString();
    }
}
