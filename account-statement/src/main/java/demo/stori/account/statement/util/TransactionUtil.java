package demo.stori.account.statement.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public final class TransactionUtil {

    private TransactionUtil() {
    }

    public static BigDecimal stringTransactionToDecimal(String amount) {
        if (amount.startsWith("+")) {
            amount = amount.substring(1);
        }

        return new BigDecimal(amount);
    }

    public static String extractDateAndResetDay(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-01");
        return df.format(date);
    }

    public static Optional<String> getMonthFromDate(String date) {
        String[] dateParts = date.split("-");

        if (dateParts.length > 3) {
            return Optional.empty();
        }

        int month = Integer.parseInt(dateParts[1]);

        String[] months = {"NA", "January", "February",
                "March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};

        if (month >= 0) {
            return Optional.of(months[month]);
        }

        return Optional.empty();
    }

}
