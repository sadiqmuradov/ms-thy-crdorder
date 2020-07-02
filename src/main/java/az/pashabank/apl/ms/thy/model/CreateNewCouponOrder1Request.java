package az.pashabank.apl.ms.thy.model;

public class CreateNewCouponOrder1Request {

    private Integer appId;
    private String name;
    private String surname;
    private String mobileNo;
    private String email;
    private String confirmEmail;


    public CreateNewCouponOrder1Request() {
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
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

    public String getConfirmEmail() {
        return confirmEmail;
    }

    public void setConfirmEmail(String confirmEmail) {
        this.confirmEmail = confirmEmail;
    }

    @Override
    public String toString() {
        return "CreateNewCouponOrder1Request{" +
                "appId=" + appId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", email='" + email + '\'' +
                ", confirmEmail='" + confirmEmail + '\'' +
                '}';
    }
}
