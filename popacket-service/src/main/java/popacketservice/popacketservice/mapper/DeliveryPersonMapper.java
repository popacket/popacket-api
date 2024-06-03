package popacketservice.popacketservice.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import popacketservice.popacketservice.model.dto.DeliveryPersonRequestDTO;
import popacketservice.popacketservice.model.dto.DeliveryPersonResponseDTO;
import popacketservice.popacketservice.model.entity.DeliveryPerson;
import org.modelmapper.ModelMapper;

import java.util.List;

@Component
@AllArgsConstructor
public class DeliveryPersonMapper {

    private final ModelMapper modelMapper;

    public DeliveryPerson convertToEntity(DeliveryPersonRequestDTO deliveryPersonRequestDTO) {
        return modelMapper.map(deliveryPersonRequestDTO, DeliveryPerson.class);
    }

    public DeliveryPersonResponseDTO convertToDTO(DeliveryPerson deliveryPerson) {
        return modelMapper.map(deliveryPerson, DeliveryPersonResponseDTO.class);
    }

    public List<DeliveryPersonResponseDTO> convertToListDTO(List<DeliveryPerson> deliveryPeople) {
        return deliveryPeople.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
