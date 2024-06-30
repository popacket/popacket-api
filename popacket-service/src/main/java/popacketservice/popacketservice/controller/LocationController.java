package popacketservice.popacketservice.controller;

import popacketservice.popacketservice.model.dto.LocationRequestDTO;
import popacketservice.popacketservice.model.dto.LocationResponseDTO;
import popacketservice.popacketservice.service.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;

@RestController

@RequestMapping("/location")
@AllArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping("/registre-location")
    public ResponseEntity<LocationResponseDTO> createLocation(@RequestBody LocationRequestDTO locationRequestDTO) {
        LocationResponseDTO locationResponseDTO = locationService.createLocation(locationRequestDTO);
        return ResponseEntity.ok(locationResponseDTO);
    }
}

