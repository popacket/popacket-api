package popacketservice.popacketservice.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import popacketservice.popacketservice.model.dto.AddressRequestDTO;
import popacketservice.popacketservice.model.dto.PackageRequestDTO;
import popacketservice.popacketservice.model.dto.PaymentResponseDTO;
import popacketservice.popacketservice.service.ShipmentService;

@RestController
@RequestMapping("/Shipment")
@Data
@AllArgsConstructor
public class ShipmentController {

    private final ShipmentService shipmentService;
    /**,@Validated @RequestBody AddressRequestDTO addressOrigin,
     @Validated @RequestBody AddressRequestDTO addressDestination*/
    /**, addressOrigin, addressDestination*/
    @PostMapping("/cost")
    public ResponseEntity<PaymentResponseDTO> shippingCost(@RequestBody PackageRequestDTO packageRequestDTO){

        PaymentResponseDTO payment = shipmentService.getShippingCost(packageRequestDTO);

    return new ResponseEntity<>(payment, HttpStatus.OK);
    }
}
