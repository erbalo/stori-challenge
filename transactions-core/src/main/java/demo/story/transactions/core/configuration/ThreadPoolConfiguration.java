package demo.story.transactions.core.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

import static org.apache.logging.log4j.util.Chars.SPACE;

@Slf4j
@EnableAsync
@Configuration
public class ThreadPoolConfiguration implements AsyncConfigurer {

    @Override
    @Bean("threadPoolExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(200);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("tx-core-thread-pool");

        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            StringBuilder builder = new StringBuilder();
            builder
                    .append("Exception message - ").append(throwable.getMessage()).append(SPACE)
                    .append("Method name - ").append(method.getName()).append(SPACE);

            for (Object object : objects) {
                builder.append("Parameter value - ").append(object);
            }

            log.error(builder.toString());
        };
    }

}