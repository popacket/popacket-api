package popacketservice.popacketservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ShipmentResponseDTO {
    private Long id;
    private LocalDate shipmentDate;
    private String description;
    private String status;
    private AddressResponseDTO originAddress;
    private AddressResponseDTO destinationAddress;
    private UserResponseDTO user;
    private PackageResponseDTO packageId;
}
