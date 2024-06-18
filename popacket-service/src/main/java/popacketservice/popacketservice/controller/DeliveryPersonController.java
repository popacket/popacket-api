package popacketservice.popacketservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
