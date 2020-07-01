/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.pashabank.apl.ms.thy.mapper;

import az.pashabank.apl.ms.thy.model.CRSAnswer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CrsAnswerRowMapper implements RowMapper<CRSAnswer> {

    @Override
    public CRSAnswer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CRSAnswer(
                rs.getInt("question_id"),
                rs.getInt("answer"),
                rs.getString("description")
        );
    }

}
