package az.pashabank.apl.ms.thy.model.thy;

public class CheckTkResponse {

    private String name;
    private String surname;
    private String totalMiles;

    public CheckTkResponse() {
    }

    public CheckTkResponse(String name, String surname, String totalMiles) {
        this.name = name;
        this.surname = surname;
        this.totalMiles = totalMiles;
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

    public String getTotalMiles() {
        return totalMiles;
    }

    public void setTotalMiles(String totalMiles) {
        this.totalMiles = totalMiles;
    }

    @Override
    public String toString() {
        return "CheckTkResponse { " +
                "name=" + name + ", " +
                "surname=" + surname + ", " +
                "totalMiles=" + totalMiles + " " +
                "}";
    }

}
