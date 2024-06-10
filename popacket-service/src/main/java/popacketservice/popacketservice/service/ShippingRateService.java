package popacketservice.popacketservice.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import popacketservice.popacketservice.mapper.ShippingRateMapper;
import popacketservice.popacketservice.model.dto.ShippingRateResponseDTO;
import popacketservice.popacketservice.model.entity.ShippingRate;
import popacketservice.popacketservice.repository.ShippingRateRepository;

import java.util.List;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ShippingRateService {

    @Autowired
    private ShippingRateRepository shippingRateRepository;
    @Autowired
    private ShippingRateMapper shippingRateMapper;

    public List<ShippingRateResponseDTO> getAllShippingRates() {
        List<ShippingRate> shippingrates = shippingRateRepository.findAll();
        return shippingRateMapper.convertToListDto(shippingrates);
    }
}
