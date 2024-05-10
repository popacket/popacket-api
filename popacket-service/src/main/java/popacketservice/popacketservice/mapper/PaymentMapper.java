package popacketservice.popacketservice.mapper;

import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import popacketservice.popacketservice.model.dto.PaymentRequestDTO;
import popacketservice.popacketservice.model.dto.PaymentResponseDTO;
import popacketservice.popacketservice.model.entity.Payment;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Data
public class PaymentMapper {
    private ModelMapper modelMapper;

    public Payment convertToEntity(PaymentRequestDTO paymentRequestDTO){
        return modelMapper.map(paymentRequestDTO, Payment.class);
    }
    public PaymentResponseDTO convertToDto(Payment payment){
        return modelMapper.map(payment, PaymentResponseDTO.class);
    }
    public List<PaymentResponseDTO> convertToListDto(List<Payment> payments){
        return payments.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
