package popacketservice.popacketservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import popacketservice.popacketservice.model.dto.EmailRequestDTO;
import popacketservice.popacketservice.service.PasswordResetService;
import popacketservice.popacketservice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/forgot-password/{email}")
    public ResponseEntity<String> forgotPassword(@PathVariable("email") String email) {
        System.out.println(email);
        return ResponseEntity.ok(passwordResetService.initiatePasswordReset(email));
    }

    @PostMapping("/reset-password/{token}/{newPass}")
    public ResponseEntity<String> resetPassword(@PathVariable("token") String token, @PathVariable("newPass") String newPassword) {
        passwordResetService.resetPassword(token, newPassword);
        return ResponseEntity.ok("Password reset successful");
    }


}