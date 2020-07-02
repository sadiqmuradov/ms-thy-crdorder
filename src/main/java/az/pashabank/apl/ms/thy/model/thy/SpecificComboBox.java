package az.pashabank.apl.ms.thy.model.thy;


import net.logstash.logback.encoder.org.apache.commons.lang.builder.ToStringBuilder;

public class SpecificComboBox {

    private String comboBoxName;

    public SpecificComboBox() {
    }

    public SpecificComboBox(String comboBoxName) {
        this.comboBoxName = comboBoxName;
    }

    public String getComboBoxName() {
        return comboBoxName;
    }

    public void setComboBoxName(String comboBoxName) {
        this.comboBoxName = comboBoxName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("comboBoxName", comboBoxName)
                .toString();
    }
}
