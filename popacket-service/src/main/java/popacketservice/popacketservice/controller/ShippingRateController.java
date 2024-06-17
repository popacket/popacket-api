package popacketservice.popacketservice.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import popacketservice.popacketservice.service.ShippingRateService;

import java.util.List;

@RestController
@RequestMapping("/ShippingRates")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingRateController {

    @Autowired
    private ShippingRateService shippingRateService;

    @GetMapping("/getShipment")
    public ResponseEntity<List<String>> getShippingRates() {
        List<String> shippingRates = shippingRateService.getAllShippingRate();
        return  new ResponseEntity<>(shippingRates, HttpStatus.OK);
    }
}
