package popacketservice.popacketservice.controller;

import lombok.AllArgsConstructor;
import org.springdoc.core.parsers.ReturnTypeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import popacketservice.popacketservice.model.dto.DeliveryPersonRequestDTO;
import popacketservice.popacketservice.model.dto.DeliveryPersonResponseDTO;
import popacketservice.popacketservice.model.entity.DeliveryPerson;
import popacketservice.popacketservice.service.DeliveryPersonService;

import java.util.List;

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

    @GetMapping
    public List<DeliveryPerson> getAllDeliveryPersons() {
        return deliveryPersonService.getAllDeliveryPersons();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDeliveryPerson(@PathVariable("id") Long id){
        String respuesta = deliveryPersonService.deleteDeliveryPersonById(id);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
