package popacketservice.popacketservice.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import popacketservice.popacketservice.model.dto.ShippingRateResponseDTO;
import popacketservice.popacketservice.model.entity.ShippingRate;

import java.util.List;

@Component
@AllArgsConstructor
@Data
public class ShippingRateMapper {

    private final ModelMapper modelMapper;

    public ShippingRate convertToEntity(ShippingRateResponseDTO shippingRateResponseDTO){
        return modelMapper.map(shippingRateResponseDTO, ShippingRate.class);
    }
    public ShippingRateResponseDTO convertToDto(ShippingRate shippingRate){
        return modelMapper.map(shippingRate, ShippingRateResponseDTO.class);
    }
    public List<ShippingRateResponseDTO> convertToListDto(List<ShippingRate> shippingRates){
        return shippingRates.stream()
                .map(this::convertToDto)
                .toList();
    }
}
