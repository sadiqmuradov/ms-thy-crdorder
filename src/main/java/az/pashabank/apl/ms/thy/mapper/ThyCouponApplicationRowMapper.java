/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.pashabank.apl.ms.thy.mapper;

import az.pashabank.apl.ms.thy.constants.CouponType;
import az.pashabank.apl.ms.thy.model.Branch;
import az.pashabank.apl.ms.thy.model.ThyCouponApplication;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ThyCouponApplicationRowMapper implements RowMapper<ThyCouponApplication> {

    @Override
    public ThyCouponApplication mapRow(ResultSet rs, int rowNum) throws SQLException {
        ThyCouponApplication app = new ThyCouponApplication();

        app.setId(rs.getInt("id"));
        app.setName(rs.getString("name"));
        app.setSurname(rs.getString("surname"));
        app.setMobileNo(rs.getString("mobile_number"));
        app.setEmail(rs.getString("email"));

        if (rs.getString("coupon_type") != null && !rs.getString("coupon_type").trim().isEmpty()) {
            app.setCouponType(CouponType.valueOf(rs.getString("coupon_type")));
        }

        app.setBranch(new Branch(rs.getString("branch_code"), rs.getString("branch_name")));
        app.setAmountToPay(rs.getBigDecimal("amount_to_pay"));
        app.setActive(rs.getBoolean("active"));

        return app;
    }

}
