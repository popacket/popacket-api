package popacketservice.popacketservice.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import popacketservice.popacketservice.mapper.ShippingRateMapper;
import popacketservice.popacketservice.model.dto.ShippingRateRequestDTO;
import popacketservice.popacketservice.model.dto.ShippingRateResponseDTO;
import popacketservice.popacketservice.model.entity.ShippingRate;
import popacketservice.popacketservice.repository.ShippingRateRepository;

@Service
@AllArgsConstructor
public class ShippingRateService {
    @Autowired
    private ShippingRateRepository shippingRateRepository;
    @Autowired
    private ShippingRateMapper shippingRateMapper;
    public ShippingRateResponseDTO registerShippingRate(ShippingRateRequestDTO shippingRate) {
        if(!(shippingRateRepository.existsByBasePriceAndPricePerKilometer
                (shippingRate.getBasePrice().doubleValue(),
                        shippingRate.getPricePerKilometer().doubleValue()))) {
            ShippingRate newShippingRate = new ShippingRate();
            newShippingRate.setBasePrice(shippingRate.getBasePrice());
            newShippingRate.setPricePerKilometer(shippingRate.getPricePerKilometer());
            newShippingRate.setServiceType(shippingRate.getServiceType());
            newShippingRate.setRegion(shippingRate.getRegion());
            newShippingRate.setWeightMax(shippingRate.getWeightMax());
            newShippingRate.setWeightMin(shippingRate.getWeightMin());
            return shippingRateMapper.convertToDto(shippingRateRepository.save(newShippingRate));
        } else {
            return shippingRateMapper.convertToDto(shippingRateRepository.getByBasePriceAndPricePerKilometer(
                    shippingRate.getBasePrice().doubleValue(),shippingRate.getPricePerKilometer().doubleValue()
            ));
        }
    }
}
