package az.pashabank.apl.ms.thy.constants;

public enum CouponsSql {

    GET_COUPONS{
        @Override
        public String getSql() {
            return "SELECT a.ID, a.CARD_PRODUCT_CODE as product_code, p.name, a.status, decode(a.STATUS,1,'Unused',0,'Used','') status_name, " +
                    "a.APPID, a.COUPON_PRICE, a.SERIAL_NO, a.username FROM THY_COUPON_CODES a " +
                    "left join thy_card_products p on a.card_product_code=p.id where serial_no=?";
        }
    },

    UPDATE_COUPONS{
        @Override
        public String getSql() {
            return "UPDATE THY_COUPON_CODES a SET a.status = 0, a.username=? WHERE a.id=?";

        }
    };

    public abstract String getSql();

}

