/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.pashabank.apl.ms.thy.mapper;

import az.pashabank.apl.ms.thy.model.UploadWrapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UploadWrapperRowMapper implements RowMapper<UploadWrapper> {

    @Override
    public UploadWrapper mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new UploadWrapper(
                rs.getLong("file_size"),
                rs.getString("name"),
                rs.getString("file_location"),
                rs.getString("file_type")
        );
    }

}
