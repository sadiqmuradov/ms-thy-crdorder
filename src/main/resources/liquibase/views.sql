-- mobile numbers by pin
CREATE OR REPLACE VIEW v_mobile_numbers AS
SELECT c.unique_id_value, c.customer_no, c.customer_name1, cp.mobile_number, cg.orderby
FROM sttm_customer c, sttm_cust_personal cp, pc_customer_groups cg
WHERE c.customer_no = cp.customer_no (+)
  AND c.group_code = cg.group_code
  AND cg.pcuse = 1
