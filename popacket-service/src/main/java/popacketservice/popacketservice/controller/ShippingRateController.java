package popacketservice.popacketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import popacketservice.popacketservice.model.dto.ShippingRateRequestDTO;
import popacketservice.popacketservice.model.dto.ShippingRateResponseDTO;
import popacketservice.popacketservice.service.ShippingRateService;

@RestController
@RequestMapping("/shippingRates")
public class ShippingRateController {

    @Autowired
    private ShippingRateService shippingRateService;

    @PutMapping("/register")
    public ResponseEntity<ShippingRateResponseDTO> registerShippingRate(@RequestBody ShippingRateRequestDTO shippingRateRequestDTO) {
        return new ResponseEntity<>(shippingRateService.registerShippingRate(shippingRateRequestDTO), HttpStatus.CREATED);
    }
}
