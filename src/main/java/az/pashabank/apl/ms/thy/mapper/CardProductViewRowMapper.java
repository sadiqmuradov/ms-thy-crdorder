/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.pashabank.apl.ms.thy.mapper;

import az.pashabank.apl.ms.thy.model.CardProductView;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CardProductViewRowMapper implements RowMapper<CardProductView> {

    @Override
    public CardProductView mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CardProductView(
                rs.getInt("card_product_id"),
                rs.getString("card_product_name"),
                rs.getInt("card_count"),
                rs.getBigDecimal("total_amount").setScale(2, BigDecimal.ROUND_HALF_UP)
        );
    }

}
