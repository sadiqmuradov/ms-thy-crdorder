package az.pashabank.apl.ms.thy.model;

import java.math.BigDecimal;

public class CardProduct {

    private int id;
    private String name;
    private BigDecimal price;
    private BigDecimal urgencyPrice;

    public CardProduct() {
    }

    public CardProduct(int id, String name) {
        this.id = id;
        this.name = name;
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

    public BigDecimal getUrgencyPrice() {
        return urgencyPrice;
    }

    public void setUrgencyPrice(BigDecimal urgencyPrice) {
        this.urgencyPrice = urgencyPrice;
    }

    @Override
    public String toString() {
        return "CardProduct{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", urgencyPrice=" + urgencyPrice +
                '}';
    }

}
