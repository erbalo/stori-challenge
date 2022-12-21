package demo.stori.account.core.controller;

import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import demo.stori.account.core.domain.ErrorDetails;
import demo.stori.account.core.exception.ResourceNotFoundException;
import demo.stori.account.core.exception.ResourceNotProcessableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@Slf4j
@RestControllerAdvice
public class AdviceController {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(value = {IllegalArgumentException.class, MethodArgumentTypeMismatchException.class, MissingServletRequestParameterException.class})
    public ErrorDetails handleBadRequestException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        return ErrorDetails.builder()
                .path(request.getRequestURI())
                .message(buildMessage(e, BAD_REQUEST))
                .build();
    }


    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ErrorDetails handleMissingHeader(MissingRequestHeaderException exception, HttpServletRequest request, HttpServletResponse response) {
        return ErrorDetails.builder()
                .path(request.getRequestURI())
                .message(buildMessage(exception, BAD_REQUEST))
                .build();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorDetails handleHttpMessageNotReadableException(HttpMessageNotReadableException exception, HttpServletRequest request, HttpServletResponse response) {
        Throwable mostSpecificCause = exception.getMostSpecificCause();
        if (mostSpecificCause instanceof JsonParseException) {
            String message = mostSpecificCause.getMessage();
            String specificMessage = message.substring(0, message.lastIndexOf("at [") - 2);

            return ErrorDetails.builder()
                    .path(request.getRequestURI())
                    .message(specificMessage)
                    .build();
        }

        JsonMappingException mappingException = (JsonMappingException) exception.getCause();
        List<String> messages = new ArrayList<>();

        mappingException.getPath().stream()
                .map(JsonMappingException.Reference::getFieldName)
                .map(field -> field.concat(" invalid"))
                .forEach(messages::add);

        return ErrorDetails.builder()
                .path(request.getRequestURI())
                .messages(messages)
                .build();
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorDetails handleNotFoundException(ResourceNotFoundException exception, HttpServletRequest request, HttpServletResponse response) {
        return ErrorDetails.builder()
                .path(request.getRequestURI())
                .message(buildMessage(exception, NOT_FOUND))
                .build();
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(ResourceNotProcessableException.class)
    public ErrorDetails handleResourceNotProcessableException(ResourceNotProcessableException exception, HttpServletRequest request, HttpServletResponse response) {
        log.error("Details", exception);

        return ErrorDetails.builder()
                .path(request.getRequestURI())
                .message(buildMessage(exception, CONFLICT))
                .build();
    }

    @ResponseStatus(SERVICE_UNAVAILABLE)
    @ExceptionHandler(AmazonDynamoDBException.class)
    public ErrorDetails handleResourceNotProcessableException(AmazonDynamoDBException exception, HttpServletRequest request, HttpServletResponse response) {
        log.error("Details", exception);

        return ErrorDetails.builder()
                .path(request.getRequestURI())
                .message(SERVICE_UNAVAILABLE.getReasonPhrase())
                .build();
    }

    private String buildMessage(Exception exception, HttpStatus status) {
        StringBuilder builder = new StringBuilder(status.getReasonPhrase());

        return ofNullable(exception.getMessage())
                .map(StringBuilder::new)
                .orElse(builder)
                .toString();
    }

}
