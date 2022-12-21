package demo.stori.transactions.reader.constants;

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

    /**
     * Names
     */
    public static final String NEW_TRANSACTION_CORE_QUEUE_NAME = "core.transaction.new";
    public static final String ACCOUNT_STATEMENT_QUEUE_NAME = "notification.account.statement";

    /**
     * Routing keys
     */
    public static final String NEW_TRANSACTION_DEAD_LETTER_ROUTING_KEY = SERVICE_COMPONENT_NAME + NEW_TRANSACTION_CORE_QUEUE_NAME + EXPIRED;

    /**
     * Queues
     */
    public static final String NEW_TRANSACTION_CORE_QUEUE = SERVICE_COMPONENT_NAME + NEW_TRANSACTION_CORE_QUEUE_NAME;
    public static final String ACCOUNT_STATEMENT_QUEUE = SERVICE_COMPONENT_NAME + ACCOUNT_STATEMENT_QUEUE_NAME;

}
