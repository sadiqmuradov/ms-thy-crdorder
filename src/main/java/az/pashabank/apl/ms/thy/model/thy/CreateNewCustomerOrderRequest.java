package az.pashabank.apl.ms.thy.model.thy;

import az.pashabank.apl.ms.thy.model.CRSAnswer;
import az.pashabank.apl.ms.thy.model.UploadWrapper;

import java.math.BigDecimal;
import java.util.List;

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
    private String passportName;
    private String passportSurname;
    private String correspondenceLanguage;
    private String securityQuestion;
    private String securityAnswer;
    private String password;
    private String repeatPassword;
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

    public boolean getUrgent() {
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

    public String getCorrespondenceLanguage() {
        return correspondenceLanguage;
    }

    public void setCorrespondenceLanguage(String correspondenceLanguage) {
        this.correspondenceLanguage = correspondenceLanguage;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
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

    @Override
    public String toString() {
        return "CreateNewCustomerOrderRequest{" +
                "residency='" + residency + '\'' +
                ", nationality='" + nationality + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", middleName='" + middleName + '\'' +
                ", gender='" + gender + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", registrationCity='" + registrationCity + '\'' +
                ", registrationAddress='" + registrationAddress + '\'' +
                ", domicileCity='" + domicileCity + '\'' +
                ", domicileAddress='" + domicileAddress + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", email='" + email + '\'' +
                ", secretCode='" + secretCode + '\'' +
                ", workplace='" + workplace + '\'' +
                ", position='" + position + '\'' +
                ", urgent='" + urgent + '\'' +
                ", tkNo='" + tkNo + '\'' +
                ", passportName='" + passportName + '\'' +
                ", passportSurname='" + passportSurname + '\'' +
                ", correspondenceLanguage='" + correspondenceLanguage + '\'' +
                ", securityQuestion='" + securityQuestion + '\'' +
                ", securityAnswer='" + securityAnswer + '\'' +
                ", password='" + password + '\'' +
                ", repeatPassword='" + repeatPassword + '\'' +
                ", crsAnswers=" + crsAnswers +
                ", acceptedTerms=" + acceptedTerms +
                ", acceptedGsa=" + acceptedGsa +
                ", currency='" + currency + '\'' +
                ", cardType=" + cardType +
                ", period=" + period +
                ", branchCode='" + branchCode + '\'' +
                ", branchName='" + branchName + '\'' +
                ", amountToPay=" + amountToPay +
                ", ipAddress='" + ipAddress + '\'' +
                '}';
    }

}
