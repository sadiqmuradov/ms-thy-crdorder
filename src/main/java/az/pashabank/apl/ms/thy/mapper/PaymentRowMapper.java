/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.pashabank.apl.ms.thy.mapper;

import az.pashabank.apl.ms.thy.model.Payment;
import az.pashabank.apl.ms.thy.model.PaymentStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class PaymentRowMapper implements RowMapper<Payment> {

    @Override
    public Payment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Payment payment = new Payment(
                rs.getInt("id"),
                rs.getString("lang"),
                rs.getString("ip_address"),
                rs.getString("currency_iso"),
                rs.getBigDecimal("amount"),
                rs.getString("description"),
                rs.getString("ecomm_trans")
        );

        payment.setCreateDate(new Date(rs.getTimestamp("create_date").getTime()));
        payment.setLastUpdate(new Date(rs.getTimestamp("last_update").getTime()));
        payment.setStatus(new PaymentStatus(rs.getInt("payment_status_id"), rs.getString("payment_status")));
        payment.setTransactionId(rs.getString("transaction_id"));
        payment.setPaymentDesc(rs.getString("payment_desc"));
        payment.setAppId(rs.getInt("app_id"));

        return payment;
    }

}
