package az.pashabank.apl.ms.thy.model.thy;



import net.logstash.logback.encoder.org.apache.commons.lang.builder.ToStringBuilder;

import java.util.List;

public class SecurityQuestionsReturn {

    private List<SecurityQuestion> specificComboBoxListArray;

    public SecurityQuestionsReturn() {
    }

    public SecurityQuestionsReturn(List<SecurityQuestion> specificComboBoxListArray) {
        this.specificComboBoxListArray = specificComboBoxListArray;
    }

    public List<SecurityQuestion> getSpecificComboBoxListArray() {
        return specificComboBoxListArray;
    }

    public void setSpecificComboBoxListArray(List<SecurityQuestion> specificComboBoxListArray) {
        this.specificComboBoxListArray = specificComboBoxListArray;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("specificComboBoxListArray", specificComboBoxListArray)
                .toString();
    }
}
