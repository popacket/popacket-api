package popacketservice.popacketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import popacketservice.popacketservice.model.dto.DeliveryPersonRequestDTO;
import popacketservice.popacketservice.model.dto.DeliveryPersonResponseDTO;
import popacketservice.popacketservice.service.DeliveryPersonService;

import java.util.List;

@RestController()
@RequestMapping("/deliveryPerson")
public class DeliveryPersonController {
    @Autowired
    private DeliveryPersonService deliveryPersonService;
    @PutMapping("/register")
    public ResponseEntity<DeliveryPersonResponseDTO> registerDeliveryPerson(@RequestBody DeliveryPersonRequestDTO deliveryPersonRequestDTO){
        DeliveryPersonResponseDTO dprdtp = deliveryPersonService.registerDeliveryPerson(deliveryPersonRequestDTO);
        return new ResponseEntity<>(dprdtp, HttpStatus.CREATED);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<DeliveryPersonResponseDTO>> getAllDeliveryPersons(){
        return new ResponseEntity<>(deliveryPersonService.getAllDeliveryPerson(),HttpStatus.OK);
    }
}
