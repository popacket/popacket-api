package popacketservice.popacketservice.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Data
@AllArgsConstructor

public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public String sendEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("popacketenvios@gmail.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
            return "Email sent";
        } catch (Exception e) {
            return "Email not sent" + e.getMessage();
        }
    }
}