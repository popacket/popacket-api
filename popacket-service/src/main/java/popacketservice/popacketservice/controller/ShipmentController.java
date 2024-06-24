package popacketservice.popacketservice.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import popacketservice.popacketservice.model.dto.ShipmentResponseDTO;
import popacketservice.popacketservice.service.ShipmentService;

import java.util.List;

@RestController
@RequestMapping("/shipments")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ShipmentController {
    @Autowired
    private ShipmentService shipmentService;

    @GetMapping("/getList/{senderId}")
    public ResponseEntity<List<ShipmentResponseDTO>> getAllShipmentByID(@PathVariable("senderId") Long senderId) {
        List<ShipmentResponseDTO> shipmentResponseDTOList = shipmentService.getAllShipmentsBySenderID(senderId);
        return new ResponseEntity<>(shipmentResponseDTOList, HttpStatus.OK);
    }
}
