package demo.stori.account.statement.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedTimestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@DynamoDBTable(tableName = "account-statements")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AccountStatement {

    @DynamoDBHashKey
    @DynamoDBTypeConvertedTimestamp(pattern = "yyy-MM-dd")
    private Date date;

    @DynamoDBRangeKey
    @DynamoDBIndexHashKey(attributeName = "account_id", globalSecondaryIndexName = "account-id-idx")
    private Long accountId;

    @DynamoDBAttribute
    private BigDecimal debit;

    @DynamoDBAttribute
    private BigDecimal credit;

    @DynamoDBAttribute
    private Long transactions;

}
