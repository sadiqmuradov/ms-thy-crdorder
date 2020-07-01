/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.pashabank.apl.ms.thy.mapper;

import az.pashabank.apl.ms.thy.model.Branch;
import az.pashabank.apl.ms.thy.model.CardProduct;
import az.pashabank.apl.ms.thy.model.ThyApplication;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ThyApplicationRowMapper implements RowMapper<ThyApplication> {

    @Override
    public ThyApplication mapRow(ResultSet rs, int rowNum) throws SQLException {
        ThyApplication app = new ThyApplication();

        app.setId(rs.getInt("id"));
        app.setResidency(rs.getString("residency"));
        app.setNationality(rs.getString("nationality"));
        app.setName(rs.getString("name"));
        app.setSurname(rs.getString("surname"));
        app.setMiddleName(rs.getString("patronymic"));
        app.setGender(rs.getString("gender"));
        app.setBirthDate(rs.getString("birth_date"));
        app.setRegistrationCity(rs.getString("registration_city"));
        app.setRegistrationAddress(rs.getString("registration_address"));
        app.setDomicileCity(rs.getString("domicile_city"));
        app.setDomicileAddress(rs.getString("domicile_address"));
        app.setMobileNo(rs.getString("mobile_number"));
        app.setEmail(rs.getString("email"));
        app.setSecretCode(rs.getString("secret_code"));
        app.setWorkplace(rs.getString("workplace"));
        app.setPosition(rs.getString("position"));
        app.setUrgent(rs.getString("urgent"));
        app.setTkNo(rs.getString("tk_no"));
        app.setPassportName(rs.getString("passport_name"));
        app.setPassportSurname(rs.getString("passport_surname"));
        app.setAcceptedTerms(rs.getInt("accepted_terms"));
        app.setAcceptedGsa(rs.getInt("accepted_gsa"));
        app.setCurrency(rs.getString("currency"));
        app.setCardProduct(new CardProduct(rs.getInt("card_product_id"), rs.getString("card_name")));
        app.setPeriod(rs.getInt("period"));
        app.setBranch(new Branch(rs.getString("branch_code"), rs.getString("branch_name")));
        app.setAmountToPay(rs.getBigDecimal("amount_to_pay"));

        return app;
    }

}
