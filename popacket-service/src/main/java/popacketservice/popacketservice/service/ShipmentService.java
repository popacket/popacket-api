package popacketservice.popacketservice.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import popacketservice.popacketservice.mapper.ShipmentMapper;
import popacketservice.popacketservice.model.dto.ShipmentResponseDTO;
import popacketservice.popacketservice.model.entity.Shipment;
import popacketservice.popacketservice.repository.ShipmentRepository;
import popacketservice.popacketservice.repository.ShippingRateRepository;

import java.math.BigDecimal;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private ShippingRateRepository shipmentRateRepository;
    @Autowired
    private ShipmentMapper shipmentMapper;

    public ShipmentResponseDTO cancelShipmentById(Long id) {
        Shipment shipmentTemp = shipmentRepository.getShipmentById(id).orElseThrow();
        shipmentTemp.setStatus("cancelado");
        shipmentRepository.save(shipmentTemp);
        return shipmentMapper.convertToDTO(shipmentTemp);
    }


    public Double getShipmentCost(Double weight, String serviceType) {
        BigDecimal priceBase = shipmentRateRepository.getBasePrice(BigDecimal.valueOf(weight), serviceType);
        BigDecimal pricePerKilometer = shipmentRateRepository.getPricePerKilometer(BigDecimal.valueOf(weight), serviceType);
        Double price = pricePerKilometer.multiply(BigDecimal.valueOf(weight)).add(priceBase).doubleValue();
        return price;
    }
    public ShipmentResponseDTO getShipmentById(Long id) {
        Shipment shipmentTemp = shipmentRepository.getShipmentById(id).orElseThrow();
        //Object[] shipmentStatus = shipmentRepository.getStatusShipmentById(id).orElseThrow();
        return shipmentMapper.convertToDTO(shipmentTemp);
    }


    public Object[] getStatusShipmentById(Long id) {
        //Shipment shipmentTemp = shipmentRepository.getShipmentById(id).orElseThrow();
        Object[] shipmentTemp = shipmentRepository.getStatusShipmentByIdOb(id).orElseThrow();
        return shipmentTemp;
    }
}
