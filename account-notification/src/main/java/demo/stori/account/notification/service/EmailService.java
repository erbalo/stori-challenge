package demo.stori.account.notification.service;

import demo.stori.account.notification.representation.AccountStatementRepresentation;
import demo.stori.account.notification.representation.EmailStatementContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Service
public class EmailService {

    private static final String STORI_LOGO_IMAGE = "templates/images/logo.png";
    private static final String PNG_MIME = "image/png";

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    public EmailService(JavaMailSender emailSender, SpringTemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    public void sendStatementTemplate(EmailStatementContext emailStatementContext) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            List<AccountStatementRepresentation> statements = emailStatementContext.getStatements();
            BigDecimal balance = emailStatementContext.getAccount().getBalance();
            Integer year = emailStatementContext.getRequest().getYear();
            String toEmail = emailStatementContext.getRequest().getToEmail();
            String balanceFormatted = String.format("%,.2f", balance);

            Context context = new Context();
            context.setVariable("storiLogo", STORI_LOGO_IMAGE);
            context.setVariable("statements", statements);
            context.setVariable("balance", balanceFormatted);
            context.setVariable("year", year);

            helper.setFrom("hiring.me.pls@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("Account summary " + year);

            String html = templateEngine.process("statement.html", context);
            helper.setText(html, true);

            ClassPathResource clr = new ClassPathResource(STORI_LOGO_IMAGE);
            helper.addInline("storiLogo", clr, PNG_MIME);

            log.info("Sending email: {}", toEmail);
            emailSender.send(message);
        } catch (MessagingException e) {
            log.error("Error sending email, with details: {}", e.getMessage());
        }
    }
}
