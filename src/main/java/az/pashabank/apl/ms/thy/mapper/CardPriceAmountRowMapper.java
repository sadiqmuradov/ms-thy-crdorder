/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.pashabank.apl.ms.thy.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CardPriceAmountRowMapper implements RowMapper<BigDecimal> {

    @Override
    public BigDecimal mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getBigDecimal("amount");
    }

}
