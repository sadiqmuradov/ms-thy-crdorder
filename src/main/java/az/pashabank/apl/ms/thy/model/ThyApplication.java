package az.pashabank.apl.ms.thy.model;


import net.logstash.logback.encoder.org.apache.commons.lang.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.util.List;

public class ThyApplication {

    private Integer id;
    private String residency;
    private String nationality;
    private String name;
    private String surname;
    private String middleName;
    private String pin;
    private String gender;
    private String birthDate;
    private List<UploadWrapper> fileUploads;
    private String registrationCity;
    private String registrationAddress;
    private String domicileCity;
    private String domicileAddress;
    private String mobileNo;
    private String email;
    private String secretCode;
    private String workplace;
    private String position;
    private String urgent;
    private String tkNo;
    private String passportName;
    private String passportSurname;
    private List<CRSAnswer> crsAnswers;
    private Integer acceptedTerms;
    private Integer acceptedGsa;
    private String currency;
    private CardProduct cardProduct;
    private Integer period;
    private Branch branch;
    private BigDecimal amountToPay;
    private String ipAddress;
    private String paymentMethod;
    private String roamingNo;
    private Integer promoCodeId;

    public ThyApplication() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
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

    public String getUrgent() {
        return urgent;
    }

    public void setUrgent(String urgent) {
        this.urgent = urgent;
    }

    public String getTkNo() {
        return tkNo;
    }

    public void setTkNo(String tkNo) {
        this.tkNo = tkNo;
    }

    public String getPassportName() {
        return passportName;
    }

    public void setPassportName(String passportName) {
        this.passportName = passportName;
    }

    public String getPassportSurname() {
        return passportSurname;
    }

    public void setPassportSurname(String passportSurname) {
        this.passportSurname = passportSurname;
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

    public CardProduct getCardProduct() {
        return cardProduct;
    }

    public void setCardProduct(CardProduct cardProduct) {
        this.cardProduct = cardProduct;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getRoamingNo() {
        return roamingNo;
    }

    public void setRoamingNo(String roamingNo) {
        this.roamingNo = roamingNo;
    }

    public Integer getPromoCodeId() {
        return promoCodeId;
    }

    public void setPromoCodeId(Integer promoCodeId) {
        this.promoCodeId = promoCodeId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("residency", residency)
                .append("nationality", nationality)
                .append("name", name)
                .append("surname", surname)
                .append("middleName", middleName)
                .append("pin", pin)
                .append("gender", gender)
                .append("birthDate", birthDate)
                .append("registrationCity", registrationCity)
                .append("registrationAddress", registrationAddress)
                .append("domicileCity", domicileCity)
                .append("domicileAddress", domicileAddress)
                .append("mobileNo", mobileNo)
                .append("email", email)
                .append("secretCode", secretCode)
                .append("workplace", workplace)
                .append("position", position)
                .append("urgent", urgent)
                .append("tkNo", tkNo)
                .append("passportName", passportName)
                .append("passportSurname", passportSurname)
                .append("crsAnswers", crsAnswers)
                .append("acceptedTerms", acceptedTerms)
                .append("acceptedGsa", acceptedGsa)
                .append("currency", currency)
                .append("cardProduct", cardProduct)
                .append("period", period)
                .append("branch", branch)
                .append("amountToPay", amountToPay)
                .append("ipAddress", ipAddress)
                .append("paymentMethod", paymentMethod)
                .append("roamingNo", roamingNo)
                .append("promoCodeId", promoCodeId)
                .toString();
    }
}
