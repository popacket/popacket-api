package popacketservice.popacketservice.service;

import popacketservice.popacketservice.model.entity.PasswordResetToken;
import popacketservice.popacketservice.model.entity.User;
import popacketservice.popacketservice.repository.PasswordResetTokenRepository;
import popacketservice.popacketservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    public String initiatePasswordReset(String email) {
        if (userRepository.existsByEmail(email)) {
            User user = userRepository.findByEmail(email);
            try {
                String token = UUID.randomUUID().toString();
                PasswordResetToken passwordResetToken = new PasswordResetToken();
                passwordResetToken.setToken(token);
                passwordResetToken.setUser(user);
                passwordResetToken.setExpiryDate(LocalDateTime.now().plusHours(1));
                tokenRepository.save(passwordResetToken);

                String resetLink = "COPIE Y PEGUE ESTE TOKEN EN TU FORMULARIO: " + token ;
                emailService.sendEmail(user.getEmail(), "Password Reset Request \uD83D\uDD12", "¿COMO RESTABLECER TU CONTRASEÑA?: " + resetLink);
                return "Token enviado exitosamente";
            } catch (Exception e) {
                e.printStackTrace();
                return "Token no enviado "+e.getMessage();
            }
        }
        return "Token no encontrado";
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken passwordResetToken = tokenRepository.findByToken(token);
        if (passwordResetToken != null && passwordResetToken.getExpiryDate().isAfter(LocalDateTime.now())) {
            User user = passwordResetToken.getUser();
            user.setPass(newPassword); // Make sure to hash the password before saving
            userRepository.save(user);
            tokenRepository.delete(passwordResetToken);
        } else {
            throw new RuntimeException("Invalid or expired password reset token");
        }
    }
}