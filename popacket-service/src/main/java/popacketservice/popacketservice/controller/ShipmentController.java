package popacketservice.popacketservice.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import popacketservice.popacketservice.model.dto.ShipmentRatingDTO;
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


    @GetMapping("/cost/{weight}/{serviceType}")
    public ResponseEntity<Double> getQuoteShipment(@PathVariable("weight") Double weight, @PathVariable("serviceType") String serviceType){
        Double price = shipmentService.getShipmentCost(weight, serviceType);
        return new ResponseEntity<>(price, HttpStatus.OK);
    }
    @GetMapping("/tracking/{id}")
    public ResponseEntity<ShipmentResponseDTO> getTrackingInfoById(@PathVariable("id") Long shipmentId) {
        ShipmentResponseDTO shipment = shipmentService.getShipmentById(shipmentId);
        return new ResponseEntity<>(shipment, HttpStatus.OK);
    }
    @GetMapping("/tracking_2/{id}")
    public ResponseEntity<Object[]> getTrackingOb(@PathVariable("id") Long shipmentId) {
        Object[] shipment = shipmentService.getStatusShipmentById(shipmentId);
        return new ResponseEntity<>(shipment, HttpStatus.OK);
    }
    @PutMapping("/makeShipment")
    public ResponseEntity<ShipmentResponseDTO> makeShipment(@RequestBody ShipmentRequestDTO shipmentDTO) {
        ShipmentResponseDTO shipmentResponseDTO = shipmentService.makeShipment(shipmentDTO);
        return new ResponseEntity<>(shipmentResponseDTO, HttpStatus.OK);
    }
    @PostMapping("/updateSchedule")
    public ResponseEntity<ShipmentResponseDTO> updateScheduleShipment(@RequestBody ShipmentRequestDTO shipmentDTO) {
        ShipmentResponseDTO shipment = shipmentService.updateScheduleShipment(shipmentDTO);
        return new ResponseEntity<>(shipment, HttpStatus.OK);
    }
    @PostMapping("/rate")
    public ResponseEntity<String> rateShipment(@RequestBody ShipmentRatingDTO ratingDto) {
        shipmentService.rateShipment(ratingDto);
        return ResponseEntity.ok("Envío calificado exitosamente con una calificación de " + ratingDto.getRating() + "y comentarios: " + ratingDto.getComments());
    }
    @PostMapping("/cancel/{id}")
    public ResponseEntity<ShipmentResponseDTO> cancelShipment(@PathVariable("id") Long shipmentId) {
        ShipmentResponseDTO shipment = shipmentService.cancelShipmentById(shipmentId);
        return new ResponseEntity<>(shipment, HttpStatus.OK);
    }
}