package e_commerce_backend.features.email;

public interface EmailSender {
    void sendEmail(String to, String subject, String content);
}
