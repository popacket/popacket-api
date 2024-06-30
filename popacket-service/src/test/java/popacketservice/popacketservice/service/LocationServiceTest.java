package popacketservice.popacketservice.service;

import popacketservice.popacketservice.model.dto.LocationRequestDTO;
import popacketservice.popacketservice.model.dto.LocationResponseDTO;
import popacketservice.popacketservice.model.entity.Location;
import popacketservice.popacketservice.repository.LocationRepository;
import popacketservice.popacketservice.mapper.LocationMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private LocationMapper locationMapper;

    @InjectMocks
    private LocationService locationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateLocation_Success() {
        LocationRequestDTO requestDTO = new LocationRequestDTO("LocationName", "LocationAddress", "LocationType");
        Location locationEntity = new Location();
        LocationResponseDTO expectedResponse = new LocationResponseDTO(1L, "LocationName", "LocationAddress", "LocationType");

        when(locationMapper.convertToEntity(requestDTO)).thenReturn(locationEntity);
        when(locationRepository.existsByAddress("LocationAddress")).thenReturn(false);
        when(locationRepository.save(locationEntity)).thenReturn(locationEntity);
        when(locationMapper.convertToDTO(locationEntity)).thenReturn(expectedResponse);

        LocationResponseDTO actualResponse = locationService.createLocation(requestDTO);

        assertEquals(expectedResponse, actualResponse);
        verify(locationRepository, times(1)).existsByAddress("LocationAddress");
        verify(locationRepository, times(1)).save(locationEntity);
    }

    @Test
    public void testCreateLocation_LocationExists() {
        LocationRequestDTO requestDTO = new LocationRequestDTO("LocationName", "LocationAddress", "LocationType");
        Location existingLocationEntity = new Location(1L, "ExistingLocationName", "LocationAddress", "ExistingLocationType");
        LocationResponseDTO expectedResponse = new LocationResponseDTO(1L, "ExistingLocationName", "LocationAddress", "ExistingLocationType");

        when(locationMapper.convertToEntity(requestDTO)).thenReturn(existingLocationEntity);
        when(locationRepository.existsByAddress("LocationAddress")).thenReturn(true);
        when(locationRepository.findByAddress("LocationAddress")).thenReturn(existingLocationEntity);
        when(locationMapper.convertToDTO(existingLocationEntity)).thenReturn(expectedResponse);

        LocationResponseDTO actualResponse = locationService.createLocation(requestDTO);

        assertEquals(expectedResponse, actualResponse);
        verify(locationRepository, times(1)).existsByAddress("LocationAddress");
        verify(locationRepository, never()).save(any());
    }
}
