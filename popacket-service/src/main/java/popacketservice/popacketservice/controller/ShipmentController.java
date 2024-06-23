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

import java.math.BigDecimal;

@RestController
@RequestMapping("/shipments")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    @PutMapping("/makeShipment")
    public ResponseEntity<ShipmentResponseDTO> makeShipment(@RequestBody ShipmentRequestDTO shipmentDTO) {
        ShipmentResponseDTO shipmentResponseDTO = shipmentService.makeShipment(shipmentDTO);
        return new ResponseEntity<>(shipmentResponseDTO, HttpStatus.OK);
    }
}
