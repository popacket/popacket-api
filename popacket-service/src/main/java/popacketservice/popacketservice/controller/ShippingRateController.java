package popacketservice.popacketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import popacketservice.popacketservice.model.dto.ShippingRateRequestDTO;
import popacketservice.popacketservice.model.dto.ShippingRateResponseDTO;
import popacketservice.popacketservice.service.ShippingRateService;

import java.util.List;

@RestController
@RequestMapping("/shippingRates")
public class ShippingRateController {

    @Autowired
    private ShippingRateService shippingRateService;

    @PutMapping("/register")
    public ResponseEntity<ShippingRateResponseDTO> registerShippingRate(@RequestBody ShippingRateRequestDTO shippingRateRequestDTO) {
        return new ResponseEntity<>(shippingRateService.registerShippingRate(shippingRateRequestDTO), HttpStatus.CREATED);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<ShippingRateResponseDTO>> getAllShippingRates() {
        return new ResponseEntity<>(shippingRateService.getAllShippingRates(),HttpStatus.OK);
    }
}
