package demo.stori.account.notification.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class EmailStatementContext {

    private AccountStatementRequest request;
    private AccountRepresentation account;
    private List<AccountStatementRepresentation> statements;

}
