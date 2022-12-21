package demo.story.transactions.core.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import demo.story.transactions.core.representation.TransactionOrigin;
import demo.story.transactions.core.representation.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@DynamoDBTable(tableName = "transactions")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Transaction {

    @DynamoDBHashKey
    private Long id;

    @DynamoDBIndexHashKey(attributeName = "account_id", globalSecondaryIndexName = "account-id-idx")
    private Long accountId;

    @DynamoDBAttribute
    private BigDecimal transaction;

    @DynamoDBAttribute
    private Date date;

    @DynamoDBAttribute
    @DynamoDBTypeConvertedEnum
    private TransactionStatus status;

    @DynamoDBAttribute
    @DynamoDBTypeConvertedEnum
    private TransactionOrigin origin;

    @DynamoDBAttribute
    private String reference;

}
