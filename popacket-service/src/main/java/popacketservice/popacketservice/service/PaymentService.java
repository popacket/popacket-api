package popacketservice.popacketservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import popacketservice.popacketservice.exception.ConflictException;
import popacketservice.popacketservice.mapper.PaymentMapper;
import popacketservice.popacketservice.model.dto.PaymentRequestDTO;
import popacketservice.popacketservice.model.dto.PaymentResponseDTO;
import popacketservice.popacketservice.model.entity.Payment;
import popacketservice.popacketservice.model.entity.Package;
import popacketservice.popacketservice.repository.PackageRepository;
import popacketservice.popacketservice.repository.PaymentRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PackageRepository packageRepository;
    private final PaymentMapper paymentMapper;

    public PaymentResponseDTO registerPayment(PaymentRequestDTO paymentRequestDTO) {
        boolean resp = paymentRepository.existsByPackageId(paymentRequestDTO.getPackageId());
        if(resp){
            throw new ConflictException("El paquete ya se encuentra pagado");
        } else {
            Package pkg = packageRepository.findById(paymentRequestDTO.getPackageId())
                    .orElseThrow(() -> new RuntimeException("Paquete no encontrado"));

            Payment payment = paymentMapper.convertToEntity(paymentRequestDTO);
            payment.setPackageEntity(pkg);
            payment.setPaymentDate(LocalDateTime.now());
            paymentRepository.save(payment);
            return paymentMapper.convertToDTO(payment);
        }
    }

}
