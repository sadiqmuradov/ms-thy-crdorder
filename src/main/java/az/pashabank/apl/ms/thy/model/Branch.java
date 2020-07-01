package az.pashabank.apl.ms.thy.model;

public class Branch {

    private String branchCode;
    private String branchCity;
    private int orderby;
    private String name;
    private String address;
    private String latitude;
    private String longitude;
    private String descr;

    public Branch() {
    }

    public Branch(String branchCode, String name) {
        this.branchCode = branchCode;
        this.name = name;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchCity() {
        return branchCity;
    }

    public void setBranchCity(String branchCity) {
        this.branchCity = branchCity;
    }

    public int getOrderby() {
        return orderby;
    }

    public void setOrderby(int orderby) {
        this.orderby = orderby;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @Override
    public String toString() {
        return "Branch { " +
                "branchCode=" + branchCode + ", " +
                "branchCity=" + branchCity + ", " +
                "orderby=" + orderby +
                "name=" + name + ", " +
                "address=" + address + ", " +
                "latitude=" + latitude + ", " +
                "longitude=" + longitude + ", " +
                "descr=" + descr + " " +
                "}";
    }

}
