/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.pashabank.apl.ms.thy.mapper;

import az.pashabank.apl.ms.thy.model.PaymentField;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentFieldRowMapper implements RowMapper<PaymentField> {

    @Override
    public PaymentField mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new PaymentField(
                rs.getInt("payment_id"),
                rs.getInt("field_id"),
                rs.getString("field_value")
        );
    }

}
