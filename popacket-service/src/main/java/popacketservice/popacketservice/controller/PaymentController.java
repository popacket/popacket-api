package popacketservice.popacketservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import popacketservice.popacketservice.model.dto.PaymentRequestDTO;
import popacketservice.popacketservice.model.dto.PaymentResponseDTO;
import popacketservice.popacketservice.service.PaymentService;

@RestController
@RequestMapping("/payments")
@AllArgsConstructor
public class PaymentController {

    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> createPackage(@Validated @RequestBody PaymentRequestDTO paymentDTO){
        PaymentResponseDTO paymentResponseDTO = paymentService.registerPayment(paymentDTO);
        return new ResponseEntity<>(paymentResponseDTO, HttpStatus.CREATED);
    }
}
