package demo.stori.transactions.reader.domain;

import demo.stori.transactions.reader.representation.TransactionRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static demo.stori.transactions.reader.constants.RabbitConstants.NEW_TRANSACTION_CORE_QUEUE;

@Component
public class TransactionCoreDispatcher {

    private final RabbitTemplate rabbitTemplate;

    public TransactionCoreDispatcher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendBulkTransactions(List<TransactionRequest> requests) {
        requests.forEach(this::sendTransaction);
    }

    public void sendTransaction(TransactionRequest request) {
        rabbitTemplate.convertAndSend(NEW_TRANSACTION_CORE_QUEUE, request);
    }

}
