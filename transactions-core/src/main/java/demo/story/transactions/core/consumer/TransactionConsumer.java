package demo.story.transactions.core.consumer;

import demo.story.transactions.core.representation.TransactionRepresentation;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static demo.story.transactions.core.constants.RabbitConstants.NEW_TRANSACTION_DLQ_QUEUE;
import static demo.story.transactions.core.constants.RabbitConstants.NEW_TRANSACTION_QUEUE;

@Component
public class TransactionConsumer {

    @RabbitListener(queues = NEW_TRANSACTION_QUEUE)
    public void handleMessage(TransactionRepresentation transaction) {
        
        //throw new AmqpRejectAndDontRequeueException("some");
    }

    @RabbitListener(queues = NEW_TRANSACTION_DLQ_QUEUE)
    public void test(Message message) {
        String jsonMessage = new String(message.getBody());
    }

}
