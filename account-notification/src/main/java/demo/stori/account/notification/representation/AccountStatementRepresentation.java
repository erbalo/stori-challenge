package demo.stori.account.notification.representation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class AccountStatementRepresentation {

    private String month;

    @JsonProperty("account_id")
    private Long accountId;

    private BigDecimal debit;
    private BigDecimal credit;
    private Long transactions;

}