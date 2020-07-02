package az.pashabank.apl.ms.thy.model.thy;

import net.logstash.logback.encoder.org.apache.commons.lang.builder.ToStringBuilder;

import java.util.List;

public class SecurityQuestionsResponse {

    private List<SecurityQuestion> securityQuestions;

    public SecurityQuestionsResponse() {
    }

    public SecurityQuestionsResponse(List<SecurityQuestion> securityQuestions) {
        this.securityQuestions = securityQuestions;
    }

    public List<SecurityQuestion> getSecurityQuestions() {
        return securityQuestions;
    }

    public void setSecurityQuestions(List<SecurityQuestion> securityQuestions) {
        this.securityQuestions = securityQuestions;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("securityQuestions", securityQuestions)
                .toString();
    }

}
