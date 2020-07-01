/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.pashabank.apl.ms.thy.constants;

public class Regex {
    public static final String EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String DATE = "^\\d{2}[.]\\d{2}[.]\\d{4}$";
    public static final String LETTERS = "\\p{L}+";
    public static final String LETTER_SPACES = "[\\p{L}\\p{Space}]+";
    public static final String ALPHANUM = "[\\p{L}\\p{Nd}]+";
    public static final String NUMBER = "^[0-9]+$";
    public static final String CURRENCY = "^(AZN|USD|EUR|GBP)$";
    public static final String PHONE = "^(\\+994)[0-9]+$";
    public static final String UPLOAD_FILE = "^(image/jpeg|image/png|image/x-png|application/pdf)$";
    public static final String CARD_BIN = "^\\d{6}$";

    private Regex() {
    }
}
