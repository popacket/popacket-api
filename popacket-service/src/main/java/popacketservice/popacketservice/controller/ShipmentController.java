package popacketservice.popacketservice.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import popacketservice.popacketservice.model.dto.ShipmentRequestDTO;
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
    @GetMapping("/tracking/{id}")
    public ResponseEntity<ShipmentResponseDTO> getTrackingInfoById(@PathVariable("id") Long shipmentId) {
        ShipmentResponseDTO shipment = shipmentService.getShipmentById(shipmentId);
        return new ResponseEntity<>(shipment, HttpStatus.OK);
    }

    @GetMapping("/tracking_2/{id}")
    public ResponseEntity<Object[]> getTrackingOb(@PathVariable("id") Long shipmentId) {
        //ShipmentResponseDTO shipment = shipmentService.getShipmentById(shipmentId);
        Object[] shipment = shipmentService.getStatusShipmentById(shipmentId);
        return new ResponseEntity<>(shipment, HttpStatus.OK);
    }
}
