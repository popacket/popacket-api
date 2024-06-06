package popacketservice.popacketservice.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import popacketservice.popacketservice.model.dto.ShipmentResponseDTO;
import popacketservice.popacketservice.service.ShipmentService;

@RestController
@RequestMapping("/shipments")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ShipmentController {
    @Autowired
    private ShipmentService shipmentService;

    @PostMapping("cancel/{id}")
    public ResponseEntity<ShipmentResponseDTO> cancelShipment(@PathVariable("id") Long shipmentId) {
        ShipmentResponseDTO shipment = shipmentService.cancelShipmentById(shipmentId);
        return new ResponseEntity<>(shipment, HttpStatus.OK);
    }
}
