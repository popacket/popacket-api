package popacketservice.popacketservice.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import popacketservice.popacketservice.model.dto.PaymentRequestDTO;
import popacketservice.popacketservice.model.dto.PaymentResponseDTO;
import popacketservice.popacketservice.model.entity.Payment;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PaymentMapper {

    private final ModelMapper modelMapper;

    public Payment convertToEntity(PaymentRequestDTO paymentRequestDTO) {
        return modelMapper.map(paymentRequestDTO, Payment.class);
    }

    public PaymentResponseDTO convertToDTO(Payment payment) {
        return modelMapper.map(payment, PaymentResponseDTO.class);
    }

    public List<PaymentResponseDTO> convertToListDTO(List<Payment> payments) {
        return payments.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
