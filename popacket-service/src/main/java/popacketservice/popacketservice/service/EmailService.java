package popacketservice.popacketservice.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public void sendResetToken(String email, String token){
        //simulacion de envio de correo electronico
        System.out.println("Enviando Token de recuperacion a: "+ email);
        System.out.println("Token: "+ token);
    }
}
