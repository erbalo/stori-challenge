package demo.stori.transactions.reader.domain;

import demo.stori.transactions.reader.representation.TransactionRequest;
import demo.stori.transactions.reader.service.TransactionService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static demo.stori.transactions.reader.constants.RabbitConstants.NEW_TRANSACTION_CORE_QUEUE;

@Component
public class TransactionCoreDispatcher {

    private final RabbitTemplate rabbitTemplate;
    private final TransactionService transactionService;

    public TransactionCoreDispatcher(RabbitTemplate rabbitTemplate, TransactionService transactionService) {
        this.rabbitTemplate = rabbitTemplate;
        this.transactionService = transactionService;
    }

    public void sendBulkTransactions(String email, List<TransactionRequest> requests) {
        System.out.println("Sending transactions in batch, you can continue executing commands :D");
        requests.forEach(this::sendTransaction);
        transactionService.prepareScheduleEmail(email, requests);
    }

    public void sendTransaction(TransactionRequest request) {
        rabbitTemplate.convertAndSend(NEW_TRANSACTION_CORE_QUEUE, request);
    }

}
