package popacketservice.popacketservice.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import popacketservice.popacketservice.model.dto.PackageRequestDTO;
import popacketservice.popacketservice.model.dto.ShipmentResponseDTO;
import popacketservice.popacketservice.service.ShipmentService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/shipments")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ShipmentController {

    private ShipmentService shipmentService;

    @PostMapping("/cancel={id}")
    public ResponseEntity<ShipmentResponseDTO> cancelShipment(@PathVariable("id") Long shipmentId) {
        ShipmentResponseDTO shipment = shipmentService.cancelShipmentById(shipmentId);
        return new ResponseEntity<>(shipment, HttpStatus.OK);
    }

    @PostMapping("/cost")
    public ResponseEntity<BigDecimal> getQuoteShipment(@RequestBody PackageRequestDTO packageRequestDTO){
        BigDecimal price = shipmentService.getShipmentCost(packageRequestDTO);
        return new ResponseEntity<>(price, HttpStatus.OK);
    }
}
