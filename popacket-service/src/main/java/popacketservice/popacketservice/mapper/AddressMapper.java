package popacketservice.popacketservice.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import popacketservice.popacketservice.model.dto.AddressRequestDTO;
import popacketservice.popacketservice.model.dto.AddressResponseDTO;
import popacketservice.popacketservice.model.entity.Address;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor

public class AddressMapper {
    private final ModelMapper modelMapper;

    public Address convertToEntity(AddressRequestDTO addressRequestDTO) {
        return modelMapper.map(addressRequestDTO, Address.class);
    }

    public AddressResponseDTO convertToDTO(Address address) {
        return modelMapper.map(address, AddressResponseDTO.class);

    }

    public List<AddressResponseDTO> convertToDTO(List<Address> addresses) {
        return addresses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
