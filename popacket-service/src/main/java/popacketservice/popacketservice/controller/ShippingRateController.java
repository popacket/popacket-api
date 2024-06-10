package popacketservice.popacketservice.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import popacketservice.popacketservice.model.dto.ShippingRateResponseDTO;
import popacketservice.popacketservice.service.ShippingRateService;

import java.util.List;

@RequestMapping("/shippingrates")
@RestController
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingRateController {

    @Autowired
    private ShippingRateService shippingRateService;

    @GetMapping("/getAllShippingRates")
    public ResponseEntity<List<ShippingRateResponseDTO>> getAllShippingRates(){
        return ResponseEntity.ok(shippingRateService.getAllShippingRates());
    }
}
