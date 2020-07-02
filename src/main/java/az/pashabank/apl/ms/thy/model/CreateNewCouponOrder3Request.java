package az.pashabank.apl.ms.thy.model;

public class CreateNewCouponOrder3Request {

    private Integer appId;
    private String ipAddress;

    public CreateNewCouponOrder3Request() {
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        return "CreateNewCouponOrder3Request{" +
                "appId=" + appId +
                ", ipAddress='" + ipAddress + '\'' +
                '}';
    }
}
