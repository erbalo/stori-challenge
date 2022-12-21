package demo.stori.account.notification.controller;

import demo.stori.account.notification.domain.AccountStatementDispatcher;
import demo.stori.account.notification.representation.AccountStatementRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/emails")
public class EmailController {

    private final AccountStatementDispatcher accountStatementDispatcher;

    public EmailController(AccountStatementDispatcher accountStatementDispatcher) {
        this.accountStatementDispatcher = accountStatementDispatcher;
    }

    @PostMapping("/statement")
    public Map<String, String> sendStatementEmail(@RequestBody AccountStatementRequest request) {
        accountStatementDispatcher.queueStatementRequest(request);
        Map<String, String> message = new HashMap<>();
        message.put("message", "Email queued");

        return message;
    }

}
