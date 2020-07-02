package az.pashabank.apl.ms.thy.model;

import az.pashabank.apl.ms.thy.constants.CouponType;

import java.math.BigDecimal;
import java.util.StringJoiner;

public class ThyCouponApplication {

    private Integer id;
    private String name;
    private String surname;
    private String middleName;
    private String mobileNo;
    private String email;
    private CouponType couponType;
    private Branch branch;
    private BigDecimal amountToPay;
    private boolean active;

    public ThyCouponApplication() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public CouponType getCouponType() {
        return couponType;
    }

    public void setCouponType(CouponType couponType) {
        this.couponType = couponType;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ThyCouponApplication.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("surname='" + surname + "'")
                .add("middleName='" + middleName + "'")
                .add("mobileNo='" + mobileNo + "'")
                .add("email='" + email + "'")
                .add("couponType=" + couponType)
                .add("branch=" + branch)
                .add("amountToPay=" + amountToPay)
                .add("active=" + active)
                .toString();
    }
}
