/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.pashabank.apl.ms.thy.mapper;

import az.pashabank.apl.ms.thy.model.Card;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class CardRowMapper implements RowMapper<Card> {

    @Override
    public Card mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Card(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getBigDecimal("price"),
                new Date(rs.getTimestamp("create_date").getTime()),
                new Date(rs.getTimestamp("last_update").getTime()),
                true
        );
    }

}
