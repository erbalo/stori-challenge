package demo.story.transactions.core.client;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ClientException extends RuntimeException {

    private final HttpStatus statusCode;

    public ClientException(String message, HttpStatus statusCode){
        super(message);
        this.statusCode = statusCode;
    }

}
