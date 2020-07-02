package az.pashabank.apl.ms.thy.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "thy_card_products")
public class CardProductEntity {

    @Id
    private int id;
    private String name;
    private int urgency;
    private Date createDate;
    private Date lastUpdate;
    private boolean active;
    @Transient
    private int price;

    public CardProductEntity(int id, String name, int urgency, int price) {
        this.id = id;
        this.name = name;
        this.urgency = urgency;
        this.price = price;
    }
}
