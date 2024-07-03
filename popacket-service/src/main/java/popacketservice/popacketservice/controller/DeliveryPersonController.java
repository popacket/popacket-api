package popacketservice.popacketservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import popacketservice.popacketservice.model.dto.DeliveryPersonRequestDTO;
import popacketservice.popacketservice.model.dto.DeliveryPersonResponseDTO;
import popacketservice.popacketservice.service.DeliveryPersonService;

@RestController
@RequestMapping("/deliveryPerson")
@AllArgsConstructor

public class DeliveryPersonController {

    @Autowired
    private DeliveryPersonService deliveryPersonService;

    @PostMapping
    public ResponseEntity<DeliveryPersonResponseDTO> createDeliveryPerson(@Validated @RequestBody DeliveryPersonRequestDTO deliveryPersonRequestDTO) {
        DeliveryPersonResponseDTO createdDeliveryPerson = deliveryPersonService.RegisterDeliveryPerson(deliveryPersonRequestDTO);
        return new ResponseEntity<>(createdDeliveryPerson, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDeliveryPerson(@PathVariable("id") Long id){
        String respuesta = deliveryPersonService.deleteDeliveryPersonById(id);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
