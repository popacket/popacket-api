package popacketservice.popacketservice.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import popacketservice.popacketservice.model.dto.AddressRequestDTO;
import popacketservice.popacketservice.model.dto.PackageRequestDTO;
import popacketservice.popacketservice.model.dto.PaymentResponseDTO;
import popacketservice.popacketservice.service.ShipmentService;

@RestController
@RequestMapping("/shipments")
@Data
@AllArgsConstructor
public class ShipmentController {

    private final ShipmentService shipmentService;
    @PostMapping("/cost")
    public ResponseEntity<PaymentResponseDTO> shippingCost(@RequestBody PackageRequestDTO packageRequestDTO, AddressRequestDTO addressOrigin, AddressRequestDTO addressDestination){
    PaymentResponseDTO payment = shipmentService.getShippingCost(packageRequestDTO, addressOrigin, addressDestination);
    return new ResponseEntity<>(payment, HttpStatus.OK);
    }
}
