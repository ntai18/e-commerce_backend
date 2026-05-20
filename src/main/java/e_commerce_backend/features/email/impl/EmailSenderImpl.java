package e_commerce_backend.features.email.impl;

import e_commerce_backend.common.exception.ECommerceException;
import e_commerce_backend.common.exception.ErrorCode;
import e_commerce_backend.features.email.EmailSender;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderImpl implements EmailSender {
    @Value("${spring.mail.username}")
    private String mailAdmin;

    private final JavaMailSender mailSender;
    @Override
    public void sendEmail(String to, String subject, String content) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setFrom(mailAdmin);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content, true);
            mailSender.send(mimeMessage);
        }
        catch (ECommerceException e) {
            throw new ECommerceException(ErrorCode.AUTH_01);
        }
        catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
