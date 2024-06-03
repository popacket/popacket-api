package popacketservice.popacketservice.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import popacketservice.popacketservice.model.dto.LocationRequestDTO;
import popacketservice.popacketservice.model.dto.LocationResponseDTO;
import popacketservice.popacketservice.model.entity.Location;
import org.modelmapper.ModelMapper;

import java.util.List;

@Component
@AllArgsConstructor
public class LocationMapper {

    private final ModelMapper modelMapper;

    public Location convertToEntity(LocationRequestDTO locationRequestDTO) {
        return modelMapper.map(locationRequestDTO, Location.class);
    }

    public LocationResponseDTO convertToDTO(Location location) {
        return modelMapper.map(location, LocationResponseDTO.class);
    }

    public List<LocationResponseDTO> convertToListDTO(List<Location> locations) {
        return locations.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
