package demo.stori.account.statement.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class TransactionUtil {

    private TransactionUtil() {
    }

    public static BigDecimal stringTransactionToDecimal(String amount) {
        if (amount.startsWith("+")) {
            amount = amount.substring(1);
        }

        return new BigDecimal(amount);
    }

    public static Date extractDateAndResetDay(Date date) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-01");
        String stringDate = df.format(date);
        return df.parse(stringDate);
    }

}
