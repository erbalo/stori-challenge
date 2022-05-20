package demo.stori.transactions.reader.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class TransactionUtil {

    public static final String DATE_FORMAT_I = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private TransactionUtil() {
    }

    public static Date stringToDate(String dateString) throws ParseException {
        SimpleDateFormat formatInput = new SimpleDateFormat(DATE_FORMAT_I);
        return formatInput.parse(dateString);
    }
}
