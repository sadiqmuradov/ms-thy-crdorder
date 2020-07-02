package az.pashabank.apl.ms.thy.model;

import az.pashabank.apl.ms.thy.constants.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;
import java.util.StringJoiner;

public class CreateNewCustomerOrderRequest {

    private String residency;
    private String nationality;
    private String name;
    private String surname;
    private String middleName;
    private String gender;
    private String birthDate;
    private List<UploadWrapper> fileUploads;
    private String registrationCity;
    private String registrationAddress;
    private String domicileCity;
    private String domicileAddress;
    private String mobileNo;
    private String email;
    protected String secretCode;
    private String workplace;
    private String position;
    private boolean urgent;
    private String tkNo;
    private List<CRSAnswer> crsAnswers;
    private Integer acceptedTerms;
    private Integer acceptedGsa;
    private String currency;
    private Integer cardType;
    private Integer period;
    private String branchCode;
    private String branchName;
    private BigDecimal amountToPay;
    private String ipAddress;
    private PaymentMethod paymentMethod;
    private Integer couponId;
    private String roamingNo;

    public CreateNewCustomerOrderRequest() {
    }

    public String getResidency() {
        return residency;
    }

    public void setResidency(String residency) {
        this.residency = residency;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public List<UploadWrapper> getFileUploads() {
        return fileUploads;
    }

    public void setFileUploads(List<UploadWrapper> fileUploads) {
        this.fileUploads = fileUploads;
    }

    public String getRegistrationCity() {
        return registrationCity;
    }

    public void setRegistrationCity(String registrationCity) {
        this.registrationCity = registrationCity;
    }

    public String getRegistrationAddress() {
        return registrationAddress;
    }

    public void setRegistrationAddress(String registrationAddress) {
        this.registrationAddress = registrationAddress;
    }

    public String getDomicileCity() {
        return domicileCity;
    }

    public void setDomicileCity(String domicileCity) {
        this.domicileCity = domicileCity;
    }

    public String getDomicileAddress() {
        return domicileAddress;
    }

    public void setDomicileAddress(String domicileAddress) {
        this.domicileAddress = domicileAddress;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecretCode() {
        return secretCode;
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isUrgent() {
        return urgent;
    }

    public void setUrgent(boolean urgent) {
        this.urgent = urgent;
    }

    public String getTkNo() {
        return tkNo;
    }

    public void setTkNo(String tkNo) {
        this.tkNo = tkNo;
    }

    public List<CRSAnswer> getCrsAnswers() {
        return crsAnswers;
    }

    public void setCrsAnswers(List<CRSAnswer> crsAnswers) {
        this.crsAnswers = crsAnswers;
    }

    public Integer getAcceptedTerms() {
        return acceptedTerms;
    }

    public void setAcceptedTerms(Integer acceptedTerms) {
        this.acceptedTerms = acceptedTerms;
    }

    public Integer getAcceptedGsa() {
        return acceptedGsa;
    }

    public void setAcceptedGsa(Integer acceptedGsa) {
        this.acceptedGsa = acceptedGsa;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public BigDecimal getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(BigDecimal amountToPay) {
        this.amountToPay = amountToPay;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public String getRoamingNo() {
        return roamingNo;
    }

    public void setRoamingNo(String roamingNo) {
        this.roamingNo = roamingNo;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CreateNewCustomerOrderRequest.class.getSimpleName() + "[", "]")
                .add("residency='" + residency + "'")
                .add("nationality='" + nationality + "'")
                .add("name='" + name + "'")
                .add("surname='" + surname + "'")
                .add("middleName='" + middleName + "'")
                .add("gender='" + gender + "'")
                .add("birthDate='" + birthDate + "'")
                .add("registrationCity='" + registrationCity + "'")
                .add("registrationAddress='" + registrationAddress + "'")
                .add("domicileCity='" + domicileCity + "'")
                .add("domicileAddress='" + domicileAddress + "'")
                .add("mobileNo='" + mobileNo + "'")
                .add("email='" + email + "'")
                .add("secretCode='" + secretCode + "'")
                .add("workplace='" + workplace + "'")
                .add("position='" + position + "'")
                .add("urgent=" + urgent)
                .add("tkNo='" + tkNo + "'")
                .add("crsAnswers=" + crsAnswers)
                .add("acceptedTerms=" + acceptedTerms)
                .add("acceptedGsa=" + acceptedGsa)
                .add("currency='" + currency + "'")
                .add("cardType=" + cardType)
                .add("period=" + period)
                .add("branchCode='" + branchCode + "'")
                .add("branchName='" + branchName + "'")
                .add("amountToPay=" + amountToPay)
                .add("ipAddress='" + ipAddress + "'")
                .add("paymentMethod=" + paymentMethod)
                .add("couponId=" + couponId)
                .add("roamingNo='" + roamingNo + "'")
                .toString();
    }
}
