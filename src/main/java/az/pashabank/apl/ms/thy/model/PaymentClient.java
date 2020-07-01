package az.pashabank.apl.ms.thy.model;

public class PaymentClient {

    private int id;
    private String name;
    private String jks;

    public PaymentClient() {
    }

    public PaymentClient(int id) {
        this.id = id;
    }

    public PaymentClient(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public PaymentClient(int id, String name, String jks) {
        this.id = id;
        this.name = name;
        this.jks = jks;
    }

    public String getJks() {
        return jks;
    }

    public void setJks(String jks) {
        this.jks = jks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PaymentClient {" + "id=" + id + ", name=" + name + ", jks=" + jks + " }";
    }

}
