package demo.story.transactions.core.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.story.transactions.core.util.JsonUtil;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import static demo.story.transactions.core.constants.RabbitConstants.ATTRIBUTE_DEAD_LETTER_EXCHANGE;
import static demo.story.transactions.core.constants.RabbitConstants.ATTRIBUTE_DEAD_LETTER_ROUTING_KEY;
import static demo.story.transactions.core.constants.RabbitConstants.ATTRIBUTE_MESSAGE_TTL;
import static demo.story.transactions.core.constants.RabbitConstants.NEW_TRANSACTION_DEAD_LETTER_ROUTING_KEY;
import static demo.story.transactions.core.constants.RabbitConstants.NEW_TRANSACTION_DLQ_QUEUE;
import static demo.story.transactions.core.constants.RabbitConstants.NEW_TRANSACTION_QUEUE;
import static demo.story.transactions.core.constants.RabbitConstants.STORI_EXCHANGE;

@Primary
@EnableRabbit
@Configuration
public class RabbitConfiguration implements RabbitListenerConfigurer {

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory, SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);

        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());

        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        ObjectMapper mapper = JsonUtil.createMapper();
        return new Jackson2JsonMessageConverter(mapper);
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(JsonUtil.createMapper());

        return converter;
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(consumerJackson2MessageConverter());

        return factory;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    private Queue createQueue(String queueName, String deadLetterRoutingKey, Integer messageTtlQueue) {
        return QueueBuilder
                .durable(queueName)
                .withArgument(ATTRIBUTE_DEAD_LETTER_EXCHANGE, STORI_EXCHANGE) // The default exchange
                .withArgument(ATTRIBUTE_DEAD_LETTER_ROUTING_KEY, deadLetterRoutingKey) // Route to the queue when the TTL occurs
                .withArgument(ATTRIBUTE_MESSAGE_TTL, messageTtlQueue)
                .build();
    }

    @Bean
    public Queue newTransactionQueue() {
        return createQueue(NEW_TRANSACTION_QUEUE, NEW_TRANSACTION_DEAD_LETTER_ROUTING_KEY, 90000);
    }

    @Bean
    public Queue newTransactionDlqQueue() {
        return new Queue(NEW_TRANSACTION_DLQ_QUEUE);
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(STORI_EXCHANGE);
    }

    @Bean
    public Binding transactionDlqBinding() {
        return BindingBuilder
                .bind(newTransactionDlqQueue())
                .to(deadLetterExchange())
                .with(NEW_TRANSACTION_DEAD_LETTER_ROUTING_KEY);
    }

}
