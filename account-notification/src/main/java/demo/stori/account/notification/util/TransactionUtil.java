package demo.stori.account.notification.util;

import java.math.BigDecimal;

public final class TransactionUtil {

    private TransactionUtil() {
    }

    public static BigDecimal stringTransactionToDecimal(String amount) {
        if (amount.startsWith("+")) {
            amount = amount.substring(1);
        }

        return new BigDecimal(amount);
    }

}
