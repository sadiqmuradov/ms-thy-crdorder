package az.pashabank.apl.ms.thy.model;

import net.logstash.logback.encoder.org.apache.commons.lang.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Payment {

    private int id;
    private PaymentClient client;
    private String transactionId;
    private String language;
    private String ipAddress;
    private String currency;
    private List<PaymentField> fields;
    private BigDecimal amount;
    private String description;
    private String ecommTransaction;
    private String ecommResult;
    private String ecommResultPs;
    private String ecommResultCode;
    private String ecomm3dSecure;
    private String ecommRrn;
    private String ecommApprovalCode;
    private String ecommCardNo;
    private String ecommAav;
    private PaymentStatus status;
    private Date createDate;
    private Date lastUpdate;
    private String paymentDesc;
    private int appId;

    public Payment() { }

    public Payment(int id, String language, String ipAddress, String currency, BigDecimal amount, String description, String ecommTransaction) {
        this.id = id;
        this.language = language;
        this.ipAddress = ipAddress;
        this.currency = currency;
        this.amount = amount;
        this.description = description;
        this.ecommTransaction = ecommTransaction;
    }

    public Payment(PaymentClient client, String language, String ipAddress, String currency, BigDecimal amount, String description, String ecommTransaction, int appId) {
        this.client = client;
        this.language = language;
        this.ipAddress = ipAddress;
        this.currency = currency;
        this.amount = amount;
        this.description = description;
        this.ecommTransaction = ecommTransaction;
        this.appId = appId;
    }

    public Payment(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PaymentClient getClient() {
        return client;
    }

    public void setClient(PaymentClient client) {
        this.client = client;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEcommTransaction() {
        return ecommTransaction;
    }

    public void setEcommTransaction(String ecommTransaction) {
        this.ecommTransaction = ecommTransaction;
    }

    public String getEcommResult() {
        return ecommResult;
    }

    public void setEcommResult(String ecommResult) {
        this.ecommResult = ecommResult;
    }

    public String getEcommResultPs() {
        return ecommResultPs;
    }

    public void setEcommResultPs(String ecommResultPs) {
        this.ecommResultPs = ecommResultPs;
    }

    public String getEcommResultCode() {
        return ecommResultCode;
    }

    public void setEcommResultCode(String ecommResultCode) {
        this.ecommResultCode = ecommResultCode;
    }

    public String getEcomm3dSecure() {
        return ecomm3dSecure;
    }

    public void setEcomm3dSecure(String ecomm3dSecure) {
        this.ecomm3dSecure = ecomm3dSecure;
    }

    public String getEcommRrn() {
        return ecommRrn;
    }

    public void setEcommRrn(String ecommRrn) {
        this.ecommRrn = ecommRrn;
    }

    public String getEcommApprovalCode() {
        return ecommApprovalCode;
    }

    public void setEcommApprovalCode(String ecommApprovalCode) {
        this.ecommApprovalCode = ecommApprovalCode;
    }

    public String getEcommCardNo() {
        return ecommCardNo;
    }

    public void setEcommCardNo(String ecommCardNo) {
        this.ecommCardNo = ecommCardNo;
    }

    public String getEcommAav() {
        return ecommAav;
    }

    public void setEcommAav(String ecommAav) {
        this.ecommAav = ecommAav;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<PaymentField> getFields() {
        return fields;
    }

    public void setFields(List<PaymentField> fields) {
        this.fields = fields;
    }

    public String getPaymentDesc() {
        return paymentDesc;
    }

    public void setPaymentDesc(String paymentDesc) {
        this.paymentDesc = paymentDesc;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("client", client)
                .append("transactionId", transactionId)
                .append("language", language)
                .append("ipAddress", ipAddress)
                .append("currency", currency)
                .append("fields", fields)
                .append("amount", amount)
                .append("description", description)
                .append("ecommTransaction", ecommTransaction)
                .append("ecommResult", ecommResult)
                .append("ecommResultPs", ecommResultPs)
                .append("ecommResultCode", ecommResultCode)
                .append("ecomm3dSecure", ecomm3dSecure)
                .append("ecommRrn", ecommRrn)
                .append("ecommApprovalCode", ecommApprovalCode)
                .append("ecommCardNo", ecommCardNo)
                .append("ecommAav", ecommAav)
                .append("status", status)
                .append("createDate", createDate)
                .append("lastUpdate", lastUpdate)
                .append("paymentDesc", paymentDesc)
                .append("appId", appId)
                .toString();
    }
}
