package az.pashabank.apl.ms.thy.model.thy;

import java.util.StringJoiner;

public class RegisterCustomerInThyRequest {

    private String name;
    private String surname;
    private String nationality;
    private String birthDate;
    private String email;
    private String mobileNo;
    private String securityQuestion;
    private String securityAnswer;
    private String password;
    private String repeatPassword;
    private String corrspondanceLanguage;
    private String gender;
    private String countryCode;
    private String cityCode;
    private String address;

    public RegisterCustomerInThyRequest() {
    }

    public RegisterCustomerInThyRequest(String name, String surname, String nationality, String birthDate, String email, String mobileNo, String securityQuestion, String securityAnswer, String password, String repeatPassword, String corrspondanceLanguage, String gender, String countryCode, String cityCode, String address) {
        this.name = name;
        this.surname = surname;
        this.nationality = nationality;
        this.birthDate = birthDate;
        this.email = email;
        this.mobileNo = mobileNo;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.corrspondanceLanguage = corrspondanceLanguage;
        this.gender = gender;
        this.countryCode = countryCode;
        this.cityCode = cityCode;
        this.address = address;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
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
        return new StringJoiner(", ", RegisterCustomerInThyRequest.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("surname='" + surname + "'")
                .add("nationality='" + nationality + "'")
                .add("birthDate='" + birthDate + "'")
                .add("email='" + email + "'")
                .add("mobileNo='" + mobileNo + "'")
                .add("securityQuestion='" + securityQuestion + "'")
                .add("securityAnswer='" + securityAnswer + "'")
                .add("password='" + password + "'")
                .add("repeatPassword='" + repeatPassword + "'")
                .add("corrspondanceLanguage='" + corrspondanceLanguage + "'")
                .add("gender='" + gender + "'")
                .add("countryCode='" + countryCode + "'")
                .add("cityCode='" + cityCode + "'")
                .add("address='" + address + "'")
                .toString();
    }
}
