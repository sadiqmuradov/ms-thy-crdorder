package az.pashabank.apl.ms.thy.model.thy;

import az.pashabank.apl.ms.thy.model.MobilePhone;
import org.apache.commons.lang.builder.ToStringBuilder;

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

    public MemberProfileData() {
    }

    public MemberProfileData(String dateOfBirthDay, String dateOfBirthMonth, String dateOfBirthYear, String emailAddress, MobilePhone mobilePhone, String name, String surname, String nationality, String securityQuestion, String securityAnswer, String pinNumber, String corrspondanceLanguage, String gender, String countryCode, String cityCode, String address) {
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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("memberId", memberId)
                .append("dateOfBirthDay", dateOfBirthDay)
                .append("dateOfBirthMonth", dateOfBirthMonth)
                .append("dateOfBirthYear", dateOfBirthYear)
                .append("emailAddress", emailAddress)
                .append("mobilePhone", mobilePhone)
                .append("name", name)
                .append("surname", surname)
                .append("nationality", nationality)
                .append("securityQuestion", securityQuestion)
                .append("securityAnswer", securityAnswer)
                .append("pinNumber", pinNumber)
                .append("corrspondanceLanguage", corrspondanceLanguage)
                .append("gender", gender)
                .append("countryCode", countryCode)
                .append("cityCode", cityCode)
                .append("address", address)
                .toString();
    }

}
