package az.pashabank.apl.ms.thy.model;

public class ThyCouponCodes {

    private String id;
    private String name;
    private String productCode;
    private Integer status;
    private String statusName;
    private String appId;
    private String couponPrice;
    private String serialNo;
    private String username;



    public ThyCouponCodes() {
    }

    public ThyCouponCodes(String id, String name, String productCode, Integer status, String statusName, String appId, String couponPrice, String serialNo, String username) {
        this.id = id;
        this.name = name;
        this.productCode = productCode;
        this.status = status;
        this.statusName = statusName;
        this.appId = appId;
        this.couponPrice = couponPrice;
        this.serialNo = serialNo;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(String couponPrice) {
        this.couponPrice = couponPrice;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "ThyCouponCodes{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", productCode='" + productCode + '\'' +
                ", status=" + status +
                ", statusName='" + statusName + '\'' +
                ", appId='" + appId + '\'' +
                ", couponPrice='" + couponPrice + '\'' +
                ", serialNo='" + serialNo + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
