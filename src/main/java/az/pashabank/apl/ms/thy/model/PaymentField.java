package az.pashabank.apl.ms.thy.model;

import net.logstash.logback.encoder.org.apache.commons.lang.builder.ToStringBuilder;

public class PaymentField {

    private int paymentId;
    private int id;
    private String value;

    public PaymentField() {
    }

    public PaymentField(int paymentId, int id, String value) {
        this.paymentId = paymentId;
        this.id = id;
        this.value = value;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("paymentId", paymentId)
                .append("id", id)
                .append("value", value)
                .toString();
    }

}
