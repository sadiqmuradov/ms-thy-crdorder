package az.pashabank.apl.ms.thy.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "thy_branches")
@IdClass(BranchId.class)
public class BranchEntity {

    @Id
    @Column(name = "branch_code")
    private String code;
    @Column(name = "branch_city")
    private String city;
    private int orderby;
    private String name;
    private String address;
    private String latitude;
    private String longitude;
    private String descr;
    @Column(name = "isenabled")
    private boolean active;
    @Id
    private String lang;

    public BranchEntity(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
