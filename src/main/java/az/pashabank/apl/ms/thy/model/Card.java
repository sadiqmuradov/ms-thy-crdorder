package az.pashabank.apl.ms.thy.model;

import java.math.BigDecimal;
import java.util.Date;

public class Card {

    private int id;
    private String name;
    private BigDecimal price;
    private Date createDate;
    private Date lastUpdate;
    private boolean active;

    public Card() {
    }

    public Card(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public Card(int id, String name, BigDecimal price, Date createDate, Date lastUpdate, boolean active) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "Card { " + "id=" + id + ", name=" + name + ", price=" + price + ", createDate=" + createDate + ", lastUpdate=" + lastUpdate + ", active=" + active + " }";
    }

}
