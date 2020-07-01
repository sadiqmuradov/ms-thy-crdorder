package az.pashabank.apl.ms.thy.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;

public class Utils {

    public static final String formatBigDecimal(int maxFractionDigits, int minFractionDigits, boolean useGouping, BigDecimal amount) {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(maxFractionDigits);
        decimalFormat.setMinimumFractionDigits(minFractionDigits);
        decimalFormat.setGroupingUsed(useGouping);

        return decimalFormat.format(amount);
    }

    public static final String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

}
