package popacketservice.popacketservice.service;

import popacketservice.popacketservice.model.dto.LocationRequestDTO;
import popacketservice.popacketservice.model.dto.LocationResponseDTO;
import popacketservice.popacketservice.model.entity.Location;
import popacketservice.popacketservice.repository.LocationRepository;
import popacketservice.popacketservice.mapper.LocationMapper;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    //@Transactional
    public LocationResponseDTO createLocation(LocationRequestDTO locationRequestDTO) {
        boolean locationExists = locationRepository.existsByAddress(locationRequestDTO.getAddress());

        if (locationExists) {
            Location existingLocation = locationRepository.findByAddress(locationRequestDTO.getAddress());
            return locationMapper.convertToDTO(existingLocation);
        } else {
            Location location = locationMapper.convertToEntity(locationRequestDTO);
            Location savedLocation = locationRepository.save(location);
            return locationMapper.convertToDTO(savedLocation);
        }
    }
}


