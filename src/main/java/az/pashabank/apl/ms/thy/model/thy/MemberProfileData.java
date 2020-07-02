package az.pashabank.apl.ms.thy.model.thy;

import az.pashabank.apl.ms.thy.model.MobilePhone;

public class MemberProfileData {

    private String memberId;
    private String dateOfBirthDay;
    private String dateOfBirthMonth;
    private String dateOfBirthYear;
    private String emailAddress;
    private MobilePhone mobilePhone;
    private String name;
    private String surname;
    private String nationality;
    private String securityQuestion;
    private String securityAnswer;
    private String pinNumber;
    private String corrspondanceLanguage;
    private String gender;
    private String countryCode;
    private String cityCode;
    private String address;
    private String identityCardNo;

    public MemberProfileData() {
    }

    public MemberProfileData(String dateOfBirthDay, String dateOfBirthMonth, String dateOfBirthYear, String emailAddress, MobilePhone mobilePhone, String name, String surname, String nationality, String securityQuestion, String securityAnswer, String pinNumber, String corrspondanceLanguage, String gender, String countryCode, String cityCode, String address, String identityCardNo) {
        this.memberId = memberId;
        this.dateOfBirthDay = dateOfBirthDay;
        this.dateOfBirthMonth = dateOfBirthMonth;
        this.dateOfBirthYear = dateOfBirthYear;
        this.emailAddress = emailAddress;
        this.mobilePhone = mobilePhone;
        this.name = name;
        this.surname = surname;
        this.nationality = nationality;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.pinNumber = pinNumber;
        this.corrspondanceLanguage = corrspondanceLanguage;
        this.gender = gender;
        this.countryCode = countryCode;
        this.cityCode = cityCode;
        this.address = address;
        this.identityCardNo = identityCardNo;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getDateOfBirthDay() {
        return dateOfBirthDay;
    }

    public void setDateOfBirthDay(String dateOfBirthDay) {
        this.dateOfBirthDay = dateOfBirthDay;
    }

    public String getDateOfBirthMonth() {
        return dateOfBirthMonth;
    }

    public void setDateOfBirthMonth(String dateOfBirthMonth) {
        this.dateOfBirthMonth = dateOfBirthMonth;
    }

    public String getDateOfBirthYear() {
        return dateOfBirthYear;
    }

    public void setDateOfBirthYear(String dateOfBirthYear) {
        this.dateOfBirthYear = dateOfBirthYear;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public MobilePhone getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(MobilePhone mobilePhone) {
        this.mobilePhone = mobilePhone;
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

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
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

    public String getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(String pinNumber) {
        this.pinNumber = pinNumber;
    }

    public String getCorrspondanceLanguage() {
        return corrspondanceLanguage;
    }

    public void setCorrspondanceLanguage(String corrspondanceLanguage) {
        this.corrspondanceLanguage = corrspondanceLanguage;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdentityCardNo() {
        return identityCardNo;
    }

    public void setIdentityCardNo(String identityCardNo) {
        this.identityCardNo = identityCardNo;
    }

    @Override
    public String toString() {
        return "MemberProfileData{" +
                "memberId='" + memberId + '\'' +
                ", dateOfBirthDay='" + dateOfBirthDay + '\'' +
                ", dateOfBirthMonth='" + dateOfBirthMonth + '\'' +
                ", dateOfBirthYear='" + dateOfBirthYear + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", mobilePhone=" + mobilePhone +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", nationality='" + nationality + '\'' +
                ", securityQuestion='" + securityQuestion + '\'' +
                ", securityAnswer='" + securityAnswer + '\'' +
                ", pinNumber='" + pinNumber + '\'' +
                ", corrspondanceLanguage='" + corrspondanceLanguage + '\'' +
                ", gender='" + gender + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", address='" + address + '\'' +
                ", identityCardNo='" + identityCardNo + '\'' +
                '}';
    }

}
