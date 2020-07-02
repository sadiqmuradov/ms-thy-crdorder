package az.pashabank.apl.ms.thy.dao;

import az.pashabank.apl.ms.thy.constants.CouponsSql;
import az.pashabank.apl.ms.thy.constants.ResultCode;
import az.pashabank.apl.ms.thy.logger.MainLogger;
import az.pashabank.apl.ms.thy.mapper.CardProductViewRowMapper;
import az.pashabank.apl.ms.thy.mapper.CardRowMapper;
import az.pashabank.apl.ms.thy.mapper.CrsAnswerRowMapper;
import az.pashabank.apl.ms.thy.mapper.MailRowMapper;
import az.pashabank.apl.ms.thy.mapper.MobileNumberRowMapper;
import az.pashabank.apl.ms.thy.mapper.PaymentFieldRowMapper;
import az.pashabank.apl.ms.thy.mapper.PaymentRowMapper;
import az.pashabank.apl.ms.thy.mapper.ThyApplicationRowMapper;
import az.pashabank.apl.ms.thy.mapper.ThyCouponApplicationRowMapper;
import az.pashabank.apl.ms.thy.mapper.UploadWrapperRowMapper;
import az.pashabank.apl.ms.thy.model.Branch;
import az.pashabank.apl.ms.thy.model.CRSAnswer;
import az.pashabank.apl.ms.thy.model.CRSQuestion;
import az.pashabank.apl.ms.thy.model.Card;
import az.pashabank.apl.ms.thy.model.CardProduct;
import az.pashabank.apl.ms.thy.model.CardProductRequest;
import az.pashabank.apl.ms.thy.model.CardProductView;
import az.pashabank.apl.ms.thy.model.CouponCode;
import az.pashabank.apl.ms.thy.model.CreateNewCouponOrder1Request;
import az.pashabank.apl.ms.thy.model.CreateNewCouponOrder2Request;
import az.pashabank.apl.ms.thy.model.CreateNewCustomerOrderRequest;
import az.pashabank.apl.ms.thy.model.MobileNumbersByPin;
import az.pashabank.apl.ms.thy.model.OperationResponse;
import az.pashabank.apl.ms.thy.model.Payment;
import az.pashabank.apl.ms.thy.model.PaymentField;
import az.pashabank.apl.ms.thy.model.ThyApplication;
import az.pashabank.apl.ms.thy.model.ThyCouponApplication;
import az.pashabank.apl.ms.thy.model.ThyCouponCodes;
import az.pashabank.apl.ms.thy.model.UploadWrapper;
import az.pashabank.apl.ms.thy.model.ValidateCouponCodeRequest;
import az.pashabank.apl.ms.thy.model.thy.CheckTkResponse;
import az.pashabank.apl.ms.thy.model.thy.RegisterCustomerInThyRequest;
import az.pashabank.apl.ms.thy.model.thy.ThyUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class MainDao implements IMainDao {

    private static final MainLogger LOGGER = MainLogger.getLogger(MainDao.class);

    private static final String INTERNAL_ERROR_1 = "INTERNAL SERVER ERROR: Adding new customer order is unsuccessful";
    private static final String INTERNAL_ERROR_2 = "INTERNAL SERVER ERROR: Adding new coupon order is unsuccessful";
    private static final String INTERNAL_ERROR_3 = "INTERNAL SERVER ERROR: Updating new coupon order is unsuccessful";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public MobileNumbersByPin getMobileNumbersByPin(String pin, OperationResponse operationResponse) {
        MobileNumbersByPin mobileNumbersByPin = null;
        String sqlText = "SELECT * FROM v_mobile_numbers WHERE UPPER(unique_id_value) = UPPER(?) ORDER BY orderby";
        try {
            List<String> mobileNumbers = jdbcTemplate.query(sqlText, new Object[]{pin}, new MobileNumberRowMapper());
            mobileNumbersByPin.setMobileNumbers(mobileNumbers);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return mobileNumbersByPin;
    }

    @Override
    public boolean insertOtpCode(int otpCode, String pin) {
        boolean result = false;
        String sqlText = "UPDATE pc_otp_sms SET expire_status = 'Y' WHERE pin_code = ? AND expire_status = 'N'";
        try {
            jdbcTemplate.update(sqlText, pin);
            sqlText = "INSERT INTO pc_otp_sms(otp_code, pin_code) VALUES(?, ?)";
            jdbcTemplate.update(sqlText, otpCode, pin);
            result = true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }

    @Override
    public boolean checkOtpCode(String otpCode, String pin) {
        boolean result = false;
        String sqlText = "SELECT count(*) FROM pc_otp_sms WHERE otp_code = ? AND pin_code = ? AND expire_status = 'N'";
        try {
            int unexpiredOtpCount = jdbcTemplate.queryForObject(sqlText, Integer.class, otpCode, pin);
            result = unexpiredOtpCount > 0;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }

    @Override
    public void insertUploads(List<UploadWrapper> uploadWrapperList, Integer newAppId) {
        String sqlText = "INSERT INTO thy_uploads(id, name, file_type, file_size, file_location, app_id) " +
                "VALUES(seq_thy_uploads.nextval, ?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sqlText, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                UploadWrapper uploadWrapper = uploadWrapperList.get(i);
                preparedStatement.setString(1, uploadWrapper.getName());
                preparedStatement.setString(2, uploadWrapper.getContentType());
                preparedStatement.setLong(3, uploadWrapper.getSize());
                preparedStatement.setString(4, uploadWrapper.getLocation());
                preparedStatement.setInt(5, newAppId);
            }

            @Override
            public int getBatchSize() {
                return uploadWrapperList.size();
            }
        });
    }

    @Override
    public List<CRSQuestion> getCRSQuestions(String lang) {
        String sqlText = "SELECT a.id, a.lang, a.question, a.addquestion" +
                " FROM thy_crs_questions a where a.lang=lower(?)" +
                " order by a.id";
        List<CRSQuestion> crsQuestionList = new ArrayList<>();
        try {
            crsQuestionList = jdbcTemplate.query(sqlText, new Object[]{lang}, new BeanPropertyRowMapper(CRSQuestion.class));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return crsQuestionList;
    }

    @Override
    public void insertCRSAnswers(List<CRSAnswer> crsAnswerList, Integer newAppId) {
        String sqlText = "insert into thy_crs_answers (app_id, question_id, answer, description) values (?,?,?,?)";
        jdbcTemplate.batchUpdate(sqlText, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                CRSAnswer crsAnswer = crsAnswerList.get(i);
                preparedStatement.setInt(1, newAppId);
                preparedStatement.setInt(2, crsAnswer.getQuestionId());
                preparedStatement.setInt(3, crsAnswer.getAnswer());
                preparedStatement.setString(4, crsAnswer.getDescription());
            }

            @Override
            public int getBatchSize() {
                return crsAnswerList.size();
            }
        });
    }

    @Override
    public List<Card> getActiveCards(String currency, Integer period) {
        List<Card> cards = new ArrayList<>();
        String sqlText = "SELECT cp.*, decode(?, 'AZN', p.lcy_amount, p.fcy_amount) price " +
                "FROM v_thy_active_card_products cp, thy_card_price p " +
                "WHERE cp.id = p.card_product_id" +
                "  AND p.period = ?";
        try {
            cards = jdbcTemplate.query(sqlText, new Object[]{currency, period}, new CardRowMapper());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return cards;
    }

    @Override
    public Integer addNewApplication(CreateNewCustomerOrderRequest request, OperationResponse operationResponse, CheckTkResponse checkTkResponse) {
        Integer newAppId = null;
        try {
            SimpleJdbcCall call =
                    new SimpleJdbcCall(jdbcTemplate)
                            .withCatalogName("pkg_thy_applications")
                            .withFunctionName("add_thy_application")
                            .declareParameters(
                                    new SqlParameter("p_residency", Types.VARCHAR),
                                    new SqlParameter("p_nationality", Types.VARCHAR),
                                    new SqlParameter("p_name", Types.VARCHAR),
                                    new SqlParameter("p_surname", Types.VARCHAR),
                                    new SqlParameter("p_middle_name", Types.VARCHAR),
                                    new SqlParameter("p_gender", Types.VARCHAR),
                                    new SqlParameter("p_birth_date", Types.VARCHAR),
                                    new SqlParameter("p_reg_city", Types.VARCHAR),
                                    new SqlParameter("p_reg_address", Types.VARCHAR),
                                    new SqlParameter("p_domicile_city", Types.VARCHAR),
                                    new SqlParameter("p_domicile_address", Types.VARCHAR),
                                    new SqlParameter("p_mobile_no", Types.VARCHAR),
                                    new SqlParameter("p_email", Types.VARCHAR),
                                    new SqlParameter("p_secret_code", Types.VARCHAR),
                                    new SqlParameter("p_workplace", Types.VARCHAR),
                                    new SqlParameter("p_position", Types.VARCHAR),
                                    new SqlParameter("p_urgent", Types.VARCHAR),
                                    new SqlParameter("p_tk_no", Types.VARCHAR),
                                    new SqlParameter("p_passport_name", Types.VARCHAR),
                                    new SqlParameter("p_passport_surname", Types.VARCHAR),
                                    new SqlParameter("p_accepted_terms", Types.VARCHAR),
                                    new SqlParameter("p_accepted_gsa", Types.VARCHAR),
                                    new SqlParameter("p_currency", Types.VARCHAR),
                                    new SqlParameter("p_card_product_id", Types.INTEGER),
                                    new SqlParameter("p_period", Types.INTEGER),
                                    new SqlParameter("p_branch_code", Types.VARCHAR),
                                    new SqlParameter("p_branch_name", Types.VARCHAR),
                                    new SqlParameter("p_amount_to_pay", Types.NUMERIC),
                                    new SqlParameter("p_payment_method", Types.VARCHAR),
                                    new SqlParameter("p_roaming_no", Types.VARCHAR),
                                    new SqlOutParameter("return", Types.INTEGER)
                            );

            SqlParameterSource in =
                    new MapSqlParameterSource()
                            .addValue("p_residency", request.getResidency())
                            .addValue("p_nationality", request.getNationality())
                            .addValue("p_name", request.getName())
                            .addValue("p_surname", request.getSurname())
                            .addValue("p_middle_name", request.getMiddleName())
                            .addValue("p_gender", request.getGender())
                            .addValue("p_birth_date", request.getBirthDate())
                            .addValue("p_reg_city", request.getRegistrationCity())
                            .addValue("p_reg_address", request.getRegistrationAddress())
                            .addValue("p_domicile_city", request.getDomicileCity())
                            .addValue("p_domicile_address", request.getDomicileAddress())
                            .addValue("p_mobile_no", request.getMobileNo())
                            .addValue("p_email", request.getEmail())
                            .addValue("p_secret_code", request.getSecretCode())
                            .addValue("p_workplace", request.getWorkplace())
                            .addValue("p_position", request.getPosition())
                            .addValue("p_urgent", request.isUrgent() ? "Y" : "N")
                            .addValue("p_tk_no", request.getTkNo())
                            .addValue("p_passport_name", checkTkResponse.getName())
                            .addValue("p_passport_surname", checkTkResponse.getSurname())
                            .addValue("p_accepted_terms", request.getAcceptedTerms())
                            .addValue("p_accepted_gsa", request.getAcceptedGsa())
                            .addValue("p_currency", request.getCurrency())
                            .addValue("p_card_product_id", request.getCardType())
                            .addValue("p_period", request.getPeriod())
                            .addValue("p_branch_code", request.getBranchCode())
                            .addValue("p_branch_name", request.getBranchName())
                            .addValue("p_amount_to_pay", request.getAmountToPay())
                            .addValue("p_payment_method", request.getPaymentMethod())
                            .addValue("p_roaming_no", request.getRoamingNo())
                    ;

            newAppId = call.executeFunction(Integer.class, in);
            insertUploads(request.getFileUploads(), newAppId);
            insertCRSAnswers(request.getCrsAnswers(), newAppId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            operationResponse.setMessage(INTERNAL_ERROR_1);
        }
        return newAppId;
    }

    @Override
    public Integer addNewCouponApplication(CreateNewCouponOrder1Request request, OperationResponse operationResponse) {
        Integer newAppId = null;
        try {
            SimpleJdbcCall call =
                    new SimpleJdbcCall(jdbcTemplate)
                            .withCatalogName("pkg_thy_applications")
                            .withFunctionName("add_thy_coupon_application")
                            .declareParameters(
                                    new SqlParameter("p_name", Types.VARCHAR),
                                    new SqlParameter("p_surname", Types.VARCHAR),
                                    new SqlParameter("p_mobile_no", Types.VARCHAR),
                                    new SqlParameter("p_email", Types.VARCHAR),
                                    new SqlOutParameter("return", Types.INTEGER)
                            );

            SqlParameterSource in =
                    new MapSqlParameterSource()
                            .addValue("p_name", request.getName())
                            .addValue("p_surname", request.getSurname())
                            .addValue("p_mobile_no", request.getMobileNo())
                            .addValue("p_email", request.getEmail())
                    ;

            newAppId = call.executeFunction(Integer.class, in);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            operationResponse.setMessage(INTERNAL_ERROR_2);
        }
        return newAppId;
    }

    @Override
    public boolean updateCouponApplicationStep1(CreateNewCouponOrder1Request request, OperationResponse operationResponse) {
        boolean result = false;
        String sqlText = "UPDATE thy_coupon_applications\n" +
                         "SET name = ?,\n" +
                         "    surname = ?,\n" +
                         "    mobile_number = ?,\n" +
                         "    email = ?,\n" +
                         "    step = ?\n" +
                         "WHERE id = ?";
        try {
            jdbcTemplate.update(
                    sqlText, request.getName(), request.getSurname(),
                    request.getMobileNo(), request.getEmail(), 1, request.getAppId()
            );
            result = true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            LOGGER.error("updateCouponApplicationStep1 Failed. createNewCouponOrder1Request: {}", request);
            operationResponse.setMessage(INTERNAL_ERROR_3);
        }
        return result;
    }

    @Override
    public boolean updateCouponApplicationStep2(CreateNewCouponOrder2Request request, OperationResponse operationResponse) {
        boolean result = false;
        String sqlText = "UPDATE thy_coupon_applications\n" +
                         "SET coupon_type = ?,\n" +
                         "    branch_code = ?,\n" +
                         "    branch_name = ?,\n" +
                         "    amount_to_pay = ?,\n" +
                         "    shipping_address = ?,\n" +
                         "    step = ?\n" +
                         "WHERE id = ?";
        try {
            jdbcTemplate.update(
                    sqlText,
                    request.getCouponType().toString(), request.getBranchCode(), request.getBranchName(),
                    request.getAmountToPay(), request.getShippingAddress(), 2, request.getAppId()
            );
            for (CardProductRequest cardProductRequest : request.getCardProductRequests()) {
                sqlText = "INSERT INTO thy_coupon_app_card_product(app_id, card_product_id, card_count) VALUES(?, ?, ?)";
                jdbcTemplate.update(sqlText, request.getAppId(), cardProductRequest.getCardType(), cardProductRequest.getCardCount());
            }
            result = true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            LOGGER.error("updateCouponApplication Failed. createNewCouponOrderRequest: {}", request);
            operationResponse.setMessage(INTERNAL_ERROR_3);
        }
        return result;
    }

    @Override
    public boolean updateCouponApplicationStep3(int appId, OperationResponse operationResponse) {
        boolean result = false;
        String sqlText = "UPDATE thy_coupon_applications SET active = ?, step = ? WHERE id = ?";
        try {
            jdbcTemplate.update(sqlText, 1, 3, appId);
            result = true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            LOGGER.error("updateCouponApplicationStep3 Failed. appId: {}", appId);
            operationResponse.setMessage(INTERNAL_ERROR_3);
        }
        return result;
    }

    @Override
    public String registerPayment(Payment payment, String orderType) {
        String tranId = null;
        String functionName;
        if (orderType.equals("COUPON")) {
            functionName = "register_transaction_coupon";
        } else {
            functionName = "register_transaction";
        }
        try {
            SimpleJdbcCall call =
                    new SimpleJdbcCall(jdbcTemplate)
                            .withCatalogName("pkg_payments")
                            .withFunctionName(functionName)
                            .declareParameters(
                                    new SqlParameter("p_client_id", Types.INTEGER),
                                    new SqlParameter("p_ecomm_trans", Types.VARCHAR),
                                    new SqlParameter("p_amount", Types.NUMERIC),
                                    new SqlParameter("p_currency", Types.VARCHAR),
                                    new SqlParameter("p_ip_address", Types.VARCHAR),
                                    new SqlParameter("p_description", Types.VARCHAR),
                                    new SqlParameter("p_lang", Types.VARCHAR),
                                    new SqlParameter("p_app_id", Types.INTEGER),
                                    new SqlOutParameter("return", Types.VARCHAR)
                            );

            SqlParameterSource in =
                    new MapSqlParameterSource()
                            .addValue("p_client_id", payment.getClient().getId())
                            .addValue("p_ecomm_trans", payment.getEcommTransaction())
                            .addValue("p_amount", payment.getAmount())
                            .addValue("p_currency", payment.getCurrency())
                            .addValue("p_ip_address", payment.getIpAddress())
                            .addValue("p_description", payment.getDescription())
                            .addValue("p_lang", payment.getLanguage())
                            .addValue("p_app_id", payment.getAppId())
                    ;

            tranId = call.executeFunction(String.class, in);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return tranId;
    }

    @Override
    public Payment getNewPaymentByEcommTransId(String ecommTransId) {
        Payment payment = null;
        String sqlText = "SELECT * FROM v_payments WHERE ecomm_trans = ? AND payment_status_id = 1";
        try {
            payment = jdbcTemplate.queryForObject(sqlText, new Object[]{ecommTransId}, new PaymentRowMapper());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return payment;
    }

    @Override
    public Payment getPaymentByEcommTransId(String ecommTransId, String orderType) {
        Payment payment = null;
        String sqlText;
        if (orderType.equals("COUPON")) {
            sqlText = "SELECT * FROM v_payments_coupon WHERE ecomm_trans = ?";
        } else {
            sqlText = "SELECT * FROM v_payments WHERE ecomm_trans = ?";
        }
        try {
            payment = jdbcTemplate.queryForObject(sqlText, new Object[]{ecommTransId}, new PaymentRowMapper());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return payment;
    }

    @Override
    public Payment getPaymentByAppId(int appId, String orderType) {
        Payment payment = null;
        String sqlText;
        if (orderType.equals("COUPON")) {
            sqlText = "SELECT * FROM v_payments_coupon WHERE app_id = ?";
        } else {
            sqlText = "SELECT * FROM v_payments WHERE app_id = ?";
        }
        try {
            payment = jdbcTemplate.queryForObject(sqlText, new Object[]{appId}, new PaymentRowMapper());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return payment;
    }

    @Override
    public void insertPaymentFields(List<PaymentField> paymentFieldList, Integer paymentId) {
        String sqlText = "INSERT INTO payment_field_values(id, payment_id, field_id, field_value) VALUES(seq_payment_field_values.nextval,?,?,?)";
        jdbcTemplate.batchUpdate(sqlText, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                PaymentField paymentField = paymentFieldList.get(i);
                ps.setInt(1, paymentId);
                ps.setInt(2, paymentField.getId());
                ps.setString(3, paymentField.getValue());
            }

            @Override
            public int getBatchSize() {
                return paymentFieldList.size();
            }
        });
    }

    @Override
    public List<PaymentField> getPaymentFieldsByPaymentId(int paymentId) {
        List<PaymentField> paymentFields = new ArrayList<>();
        String sqlText = "SELECT * FROM payment_field_values WHERE payment_id = ?";
        try {
            paymentFields = jdbcTemplate.query(sqlText, new Object[]{paymentId}, new PaymentFieldRowMapper());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return paymentFields;
    }

    @Override
    public List<Payment> getUnpaidFlexPayments(String orderType) {
        List<Payment> list = new ArrayList<>();
        String sqlText;
        if (orderType.equals("COUPON")) {
            sqlText = "SELECT * FROM v_flex_unpaid_payments_coupon";
        } else {
            sqlText = "SELECT * FROM v_flex_unpaid_payments";
        }
        try {
            list = jdbcTemplate.query(sqlText, new PaymentRowMapper());
            /*for (Payment payment : list) {
                payment.setFields(getPaymentFieldsByPaymentId(payment.getId()));
            }*/
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return list;
    }

    @Override
    public OperationResponse<String> makePaymentToFlex(int paymentId, String orderType) {
        OperationResponse operationResponse = new OperationResponse(ResultCode.ERROR);
        String procedureName;
        if (orderType.equals("COUPON")) {
            procedureName = "make_flex_payment_coupon";
        } else {
            procedureName = "make_flex_payment";
        }
        try {
            SimpleJdbcCall call =
                    new SimpleJdbcCall(jdbcTemplate)
                            .withCatalogName("flex_payments")
                            .withProcedureName(procedureName)
                            .declareParameters(
                                    new SqlParameter("p_payment_id", Types.INTEGER),
                                    new SqlOutParameter("p_res", Types.SMALLINT),
                                    new SqlOutParameter("p_trn_ref_no", Types.VARCHAR),
                                    new SqlOutParameter("p_error_code", Types.VARCHAR)
                            );

            SqlParameterSource in =
                    new MapSqlParameterSource()
                            .addValue("p_payment_id", paymentId);

            Map<String, Object> execute = call.execute(in);

            Short res = execute.get("p_res") != null ? (Short) execute.get("p_res") : 0;
            String trnRefNo = execute.get("p_trn_ref_no") != null ? (String) execute.get("p_trn_ref_no") : "";
            String errorCode = execute.get("p_error_code") != null ? (String) execute.get("p_error_code") : "";

            if (res == 1) {
                LOGGER.info("makePaymentToFlex. Flex payment is successful. orderType: {}, paymentId: {}, res: {}", orderType, paymentId, res);
                operationResponse.setCode(ResultCode.OK);
                operationResponse.setData(trnRefNo);
            } else {
                LOGGER.error("makePaymentToFlex. orderType: {}, Input - paymentId: {}, Output - res: {}, errorCode: {}", orderType, paymentId, res, errorCode);
                operationResponse.setData(errorCode);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return operationResponse;
    }

    @Override
    public boolean markApplicationAsPaid(int appId, String orderType) {
        return markPaidOrSent("PAID", appId, orderType);
    }

    @Override
    public boolean markApplicationAsSent(int appId, String orderType) {
        return markPaidOrSent("SENT", appId, orderType);
    }

    private boolean markPaidOrSent(String markType, int appId, String orderType) {
        boolean result = false;
        StringBuilder procedureNameBuilder = new StringBuilder("mark_");
        if (orderType.equals("COUPON")) {
            procedureNameBuilder.append("coupon_");
        }
        procedureNameBuilder.append("application_as_");
        if (markType.equals("PAID")) {
            procedureNameBuilder.append("paid");
        } else {
            procedureNameBuilder.append("sent");
        }
        try {
            SimpleJdbcCall call =
                    new SimpleJdbcCall(jdbcTemplate)
                            .withCatalogName("pkg_thy_applications")
                            .withProcedureName(procedureNameBuilder.toString())
                            .declareParameters(
                                    new SqlParameter("p_app_id", Types.INTEGER)
                            );

            SqlParameterSource in =
                    new MapSqlParameterSource()
                            .addValue("p_app_id", appId);

            call.execute(in);
            result = true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public void updatePaymentStatus(Payment payment, boolean isSuccessful, String description, String orderType) {
        String procedureName;
        if (orderType.equals("COUPON")) {
            procedureName = "update_transaction_status_coupon";
        } else {
            procedureName = "update_transaction_status";
        }
        try {
            SimpleJdbcCall call =
                    new SimpleJdbcCall(jdbcTemplate)
                            .withCatalogName("pkg_payments")
                            .withProcedureName(procedureName)
                            .declareParameters(
                                    new SqlParameter("p_transaction_id", Types.VARCHAR),
                                    new SqlParameter("p_ecomm_trans", Types.VARCHAR),
                                    new SqlParameter("p_ecomm_result", Types.VARCHAR),
                                    new SqlParameter("p_ecomm_result_ps", Types.VARCHAR),
                                    new SqlParameter("p_ecomm_result_code", Types.VARCHAR),
                                    new SqlParameter("p_ecomm_3dsecure", Types.VARCHAR),
                                    new SqlParameter("p_ecomm_rrn", Types.VARCHAR),
                                    new SqlParameter("p_ecomm_approval_code", Types.VARCHAR),
                                    new SqlParameter("p_ecomm_card_number", Types.VARCHAR),
                                    new SqlParameter("p_ecomm_aav", Types.VARCHAR),
                                    new SqlParameter("p_success", Types.SMALLINT),
                                    new SqlParameter("p_payment_desc", Types.VARCHAR)
                            );

            SqlParameterSource in =
                    new MapSqlParameterSource()
                            .addValue("p_transaction_id", payment.getTransactionId())
                            .addValue("p_ecomm_trans", payment.getEcommTransaction())
                            .addValue("p_ecomm_result", payment.getEcommResult())
                            .addValue("p_ecomm_result_ps", payment.getEcommResultPs())
                            .addValue("p_ecomm_result_code", payment.getEcommResultCode())
                            .addValue("p_ecomm_3dsecure", payment.getEcomm3dSecure())
                            .addValue("p_ecomm_rrn", payment.getEcommRrn())
                            .addValue("p_ecomm_approval_code", payment.getEcommApprovalCode())
                            .addValue("p_ecomm_card_number", payment.getEcommCardNo())
                            .addValue("p_ecomm_aav", payment.getEcommAav())
                            .addValue("p_success", isSuccessful ? 1 : 0)
                            .addValue("p_payment_desc", description)
                    ;

            call.execute(in);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean updateFlexPaymentDesc(int appId, String errorCode) {
        boolean result = false;
        String sqlText = "UPDATE thy_applications SET flex_payment_desc = ? WHERE id = ?";
        try {
            jdbcTemplate.update(sqlText, errorCode, appId);
            result = true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }

    @Override
    public ThyApplication getThyApplication(int appId) {
        ThyApplication thyApplication = null;
        String sqlText = "SELECT * FROM v_thy_active_apps WHERE id = ?";
        try {
            thyApplication = jdbcTemplate.queryForObject(sqlText, new Object[]{appId}, new ThyApplicationRowMapper());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return thyApplication;
    }

    @Override
    public ThyCouponApplication getThyCouponApplication(int appId) {
        ThyCouponApplication app = null;
        String sqlText = "SELECT * FROM thy_coupon_applications WHERE id = ?";
        try {
            app = jdbcTemplate.queryForObject(sqlText, new Object[] { appId }, new ThyCouponApplicationRowMapper());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return app;
    }

    @Override
    public ThyCouponApplication getActiveCouponApplication(int appId) {
        ThyCouponApplication app = null;
        String sqlText = "SELECT * FROM v_thy_active_coupon_apps WHERE id = ?";
        try {
            app = jdbcTemplate.queryForObject(sqlText, new Object[] { appId }, new ThyCouponApplicationRowMapper());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return app;
    }

    @Override
    public List<CardProductView> getCardProductViews(int appId) {
        List<CardProductView> list = new ArrayList<>();
        String sqlText = "SELECT ccp.card_product_id, cp.name card_product_name, ccp.card_count, p.lcy_amount * ccp.card_count total_amount\n" +
                         "FROM thy_coupon_app_card_product ccp, thy_card_products cp, thy_card_price p\n" +
                         "WHERE ccp.card_product_id = cp.id\n" +
                         "  AND cp.id = p.card_product_id\n" +
                         "  AND cp.active = 1\n" +
                         "  AND ccp.app_id = ?";
        try {
            list = jdbcTemplate.query(sqlText, new Object[] { appId }, new CardProductViewRowMapper());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return list;
    }

    @Override
    public CouponCode getElectronCouponCode(int cardProductId) {
        CouponCode couponCode = null;
        String sqlText = "SELECT *\n" +
                         "FROM thy_coupon_codes\n" +
                         "WHERE type = 'E'\n" +
                         "  AND status = 1\n" +
                         "  AND card_product_code = ?\n" +
                         "  AND rownum = 1";
        try {
            couponCode = (CouponCode) jdbcTemplate.queryForObject(sqlText, new Object[] { cardProductId }, new BeanPropertyRowMapper(CouponCode.class));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return couponCode;
    }

    @Override
    public void updateElectronCouponCode(int id) {
        String sqlText = "UPDATE thy_coupon_codes SET status = 2 WHERE id = ?";
        try {
            jdbcTemplate.update(sqlText, id);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public String getValueFromEmailConfig(String key) {
        String sqlText = "SELECT value FROM thy_email_template_config WHERE key = ?";
        try {
            return jdbcTemplate.queryForObject(sqlText, String.class, key);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public String getEmailFromApp(int appId) {
        String email = null;
        String sqlText = "SELECT * FROM v_thy_active_apps WHERE id = ?";
        try {
            ThyApplication thyApplication = jdbcTemplate.queryForObject(sqlText, new Object[]{appId}, new ThyApplicationRowMapper());
            email = thyApplication.getEmail();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return email;
    }

    @Override
    public List<ThyApplication> getUnsentApplications() {
        List<ThyApplication> list = new ArrayList<>();
        String sqlText = "SELECT * FROM v_thy_unsent_apps";
        try {
            list = jdbcTemplate.query(sqlText, new ThyApplicationRowMapper());
            for (ThyApplication app : list) {
                app.setFileUploads(getUploads(app.getId()));
                app.setCrsAnswers(getCrsAnswers(app.getId()));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return list;
    }

    @Override
    public List<UploadWrapper> getUploads(Integer appId) {
        List<UploadWrapper> list = new ArrayList<>();
        String sqlText = "SELECT * FROM thy_uploads WHERE app_id = ?";
        try {
            list = jdbcTemplate.query(sqlText, new Object[]{appId}, new UploadWrapperRowMapper());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return list;
    }

    @Override
    public List<CRSAnswer> getCrsAnswers(Integer appId) {
        List<CRSAnswer> list = new ArrayList<>();
        String sqlText = "SELECT * FROM thy_crs_answers WHERE app_id = ?";
        try {
            list = jdbcTemplate.query(sqlText, new Object[]{appId}, new CrsAnswerRowMapper());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return list;
    }

    @Override
    public List<String> getActiveMails() {
        List<String> list = new ArrayList<>();
        String sqlText = "SELECT * FROM v_thy_active_mails";
        try {
            list = jdbcTemplate.query(sqlText, new MailRowMapper());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return list;
    }

    @Override
    public String addUserInfo(RegisterCustomerInThyRequest request, String tkNumber) {
        try {
            String sqlText = "insert into THY_USER_INFO(NAME,SURNAME,LANGUAGE,NATIONALITY,BIRTH_DATE,GENDER," +
                    "COUNTRY,CITY,ADDRESS,EMAIL,PHONE_NUMBER,PASSWORD,STATUS,TK_NUMBER,TC_KIMLIK) select ?,?,?,?,?,?,?,?,?,?,?,?,?,?,? from dual " +
                    "WHERE NOT EXISTS ( select tk_number from thy_user_info where tk_number=?)";

            jdbcTemplate.update(sqlText,
                    request.getName(),
                    request.getSurname(),
                    request.getCorrspondanceLanguage(),
                    request.getNationality(),
                    request.getBirthDate(),
                    request.getGender(),
                    request.getCountryCode(),
                    request.getCityCode(),
                    request.getAddress(),
                    request.getEmail(),
                    request.getMobileNo(),
                    request.getPassword(),
                    0,
                    tkNumber,
                    request.getIdentityCardNo());
            return "OK";
        } catch (Exception e) {
            LOGGER.error("Can't add user info {} ", e.getMessage());
        }
        return "ERROR";
    }

    @Override
    public boolean updateTHYuserInfo(String email, String tkNo, int status) {
        boolean result = false;
        try {
            String sqlText = "update thy_user_info set tk_number=?, status=? where email=?";
            jdbcTemplate.update(sqlText, tkNo, status, email);
            result = true;
        } catch (Exception e) {
            LOGGER.error("Can't update TK {} ", e.getMessage());
        }
        return result;
    }

    @Override
    public List<Branch> getBranchList(String lang) {
        List<Branch> branchList = new ArrayList<>();
        String sqlText = "SELECT a.branch_code, a.branch_city, a.orderby, a.name," +
                " a.address, a.latitude, a.longitude, a.descr" +
                " FROM v_thy_active_branches a where a.lang = lower(?)";
        try {
            branchList = jdbcTemplate.query(sqlText, new Object[]{lang}, new BeanPropertyRowMapper(Branch.class));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return branchList;
    }

    @Override
    public List<ThyUserInfo> getThyUsersList(int page) {
        List<ThyUserInfo> list = new ArrayList<>();
        String sqlText = "select * from (select ROWNUM AS rn,X.* from (select T.* from thy_user_info T where T.STATUS = 1 ORDER BY T.ID desc) X ) where rn BETWEEN ? and ?";
        try {
            list = jdbcTemplate.query(sqlText, new Object[]{(page - 1) * 20, page * 20}, new BeanPropertyRowMapper(ThyUserInfo.class));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return list;
    }

    @Override
    public List<ThyUserInfo> getThyUsersListByKeyword(String keyword) {
        List<ThyUserInfo> list = new ArrayList<>();
        String sqlText = "select T.* from thy_user_info T where T.STATUS = 1 and (UPPER(T.TK_NUMBER) LIKE  ? OR UPPER(T.NAME) LIKE  ? OR UPPER(T.SURNAME) LIKE  ?) and ROWNUM<21 ORDER BY T.ID desc";
        try {
            list = jdbcTemplate.query(sqlText, new Object[]{'%' + keyword.toUpperCase() + '%', '%' + keyword.toUpperCase() + '%', '%' + keyword.toUpperCase() + '%'}, new BeanPropertyRowMapper(ThyUserInfo.class));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return list;
    }

    @Override
    public int getThyUsersListCount() {
        String sqlText = "select count(*) from thy_user_info where STATUS = 1 ";
        try {
            return (int) jdbcTemplate.queryForObject(sqlText, Integer.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return 0;
        }
    }


    @Override
    public ThyUserInfo getThyUserInfoById(int userId) {
        List<ThyUserInfo> list = new ArrayList<>();
        String sqlText = "select * from thy_user_info where id=? and status=1";
        try {
            list = jdbcTemplate.query(sqlText, new Object[]{userId}, new BeanPropertyRowMapper(ThyUserInfo.class));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return list.get(0);
    }

    @Override
    public List<CardProduct> getCardProducts(String lang, String orderType) {
        List<CardProduct> cardProducts = new ArrayList<>();
        String sqlText = "SELECT a.id, a.name, b.lcy_amount as price, a.urgency as urgency_price\n" +
                         "FROM thy_card_products a, thy_card_price b\n" +
                         "WHERE a.active = 1 AND a.id = b.card_product_id";
        if (orderType.equals("CARD")) {
            sqlText += " AND a.card_sale = 1";
        } else {
            sqlText += " AND a.coupon_sale = 1";
        }
        try {
            cardProducts = jdbcTemplate.query(sqlText, new BeanPropertyRowMapper(CardProduct.class));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return cardProducts;
    }

    @Override
    public CardProduct getCardProductById(int productId) {
        CardProduct cardProduct = new CardProduct();
        String sqlText = "SELECT a.id, a.name, b.lcy_amount as price, a.urgency as urgency_price" +
                " FROM thy_card_products a, thy_card_price b" +
                " where a.active = 1 and a.id = b.card_product_id and a.id = ?";
        try {
            cardProduct = (CardProduct) jdbcTemplate.queryForObject(sqlText, new Object[]{productId}, new BeanPropertyRowMapper(CardProduct.class));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return cardProduct;
    }

    @Override
    public String getDynamicVal(String key) {
        String sqlText = "SELECT value FROM thy_data WHERE key=?";
        try {
            return jdbcTemplate.queryForObject(sqlText, String.class, key);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public CouponCode getCouponCode(int couponId) {
        CouponCode couponCode = null;
        String sqlText = "SELECT id, card_product_code, coupon_code, status, appid, coupon_price " +
                "FROM thy_coupon_codes WHERE id = ?";
        try {
            couponCode = (CouponCode) jdbcTemplate.queryForObject(
                    sqlText,
                    new Object[]{couponId},
                    new BeanPropertyRowMapper(CouponCode.class)
            );
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return couponCode;
    }

    @Override
    public boolean updateCouponAppAndStatus(int couponId, int appId) {
        boolean result = false;
        String sqlText = "UPDATE thy_coupon_codes SET appid = ?, status = 3 WHERE id = ?";
        try {
            jdbcTemplate.update(sqlText, appId, couponId);
            result = true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public String getCouponSerialNo(int appId) {
        String serialNo = null;
        String sqlText = "SELECT serial_no FROM thy_coupon_codes WHERE appid = ?";
        try {
            serialNo = jdbcTemplate.queryForObject(sqlText, String.class, appId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return serialNo;
    }

    @Override
    public String getPromoCodeById(int promoCodeId) {
        String promoCode = null;
        String sqlText = "SELECT promo_code FROM thy_promocode WHERE id = ?";
        try {
            promoCode = jdbcTemplate.queryForObject(sqlText, String.class, promoCodeId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return promoCode;
    }

    @Override
    public CouponCode getCouponCode(ValidateCouponCodeRequest validateCouponCodeRequest){
        CouponCode couponCode = null;
        String sqlText = "SELECT id, card_product_code, coupon_code, status, appid, coupon_price " +
                "FROM thy_coupon_codes where card_product_code = ? and coupon_code = ?";
        try {
            couponCode = (CouponCode) jdbcTemplate.queryForObject(sqlText,
                    new Object[]{validateCouponCodeRequest.getCardProductCode(), validateCouponCodeRequest.getCouponCode()},
                    new BeanPropertyRowMapper(CouponCode.class));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return couponCode;
    }

    @Override
    public List<ThyCouponCodes> getThyCouponCodes(String serialNo) {
        List<ThyCouponCodes> list = new ArrayList<>();
        String sqlText = CouponsSql.GET_COUPONS.getSql();
        try {
            list = jdbcTemplate.query(sqlText, new Object[]{serialNo}, new BeanPropertyRowMapper(ThyCouponCodes.class));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void updateCouponCodes(String id, String username) {

        String sqlText1 = CouponsSql.UPDATE_COUPONS.getSql();
        try {
            int[] types = {Types.VARCHAR, Types.VARCHAR};
            jdbcTemplate.update(sqlText1, new Object[]{username,id}, types);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }



}