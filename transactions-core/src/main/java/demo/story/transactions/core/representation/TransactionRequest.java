package demo.story.transactions.core.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class TransactionRequest {

    private Long id;
    private String transaction;
    private Long account;
    private Date date;
    private TransactionOrigin origin;
    private String reference;

}
