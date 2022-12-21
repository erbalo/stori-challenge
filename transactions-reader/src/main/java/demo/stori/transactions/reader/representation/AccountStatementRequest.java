package demo.stori.transactions.reader.representation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class AccountStatementRequest {

    @JsonProperty("to_email")
    private String toEmail;

    @JsonProperty("account_id")
    private Long accountId;

    @Builder.Default
    private Integer year = new GregorianCalendar().get(Calendar.YEAR);

}
