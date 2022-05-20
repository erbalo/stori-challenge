package demo.stori.account.statement.util;

import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

    public static Date extractDateAndResetDay(Date date) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-01");
        String stringDate = df.format(date);
        return df.parse(stringDate);
    }

    public static Optional<String> getMonthFromDate(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        int month = calendar.get(Calendar.MONTH);

        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();

        if (month >= 0 && month <= 11) {
            return Optional.of(months[month]);
        }

        return Optional.empty();
    }

}
