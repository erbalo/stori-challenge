package demo.story.transactions.core.constants;

public final class RabbitConstants {

    private RabbitConstants() {
    }

    /**
     * Attributes
     */
    public static final String ATTRIBUTE_DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";
    public static final String ATTRIBUTE_DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";
    public static final String ATTRIBUTE_MESSAGE_TTL = "x-message-ttl";

    /**
     * components
     */
    public static final String SERVICE_COMPONENT_NAME = "demo.stori.";
    public static final String STORI_EXCHANGE = "stori.direct";
    public static final String EXPIRED = ".expired";
    public static final String DLQ = ".dlq";

    /**
     * Names
     */
    public static final String NEW_TRANSACTION_QUEUE_NAME = "core.transaction.new";
    public static final String NEW_TRANSACTION_ACCOUNT_STATEMENT_QUEUE_NAME = "account.statement.transaction.new";

    /**
     * Routing keys
     */
    public static final String NEW_TRANSACTION_DEAD_LETTER_ROUTING_KEY = SERVICE_COMPONENT_NAME + NEW_TRANSACTION_QUEUE_NAME + EXPIRED;

    /**
     * Queues
     */
    public static final String NEW_TRANSACTION_QUEUE = SERVICE_COMPONENT_NAME + NEW_TRANSACTION_QUEUE_NAME;
    public static final String NEW_TRANSACTION_ACCOUNT_STATEMENT_QUEUE = SERVICE_COMPONENT_NAME + NEW_TRANSACTION_ACCOUNT_STATEMENT_QUEUE_NAME;

    /**
     * Dead letters
     */
    public static final String NEW_TRANSACTION_DLQ_QUEUE = SERVICE_COMPONENT_NAME + NEW_TRANSACTION_QUEUE_NAME + DLQ;
}
