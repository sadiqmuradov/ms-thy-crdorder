package az.pashabank.apl.ms.thy.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "thy_coupon_codes")
public class CouponCodeEntity {

    @Id
    private Integer id;
    private Integer cardProductCode;
    private String couponCode;
    private Integer status;
    private Integer appid;
    private Integer couponPrice;
    private String serialNo;
    private String username;
    private String type;
    private String branchCode;

}
