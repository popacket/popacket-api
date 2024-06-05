package popacketservice.popacketservice.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import popacketservice.popacketservice.mapper.ShipmentMapper;
import popacketservice.popacketservice.model.dto.ShipmentResponseDTO;
import popacketservice.popacketservice.model.entity.Shipment;
import popacketservice.popacketservice.repository.ShipmentRepository;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ShipmentService {
    private ShipmentRepository shipmentRepository;
    private ShipmentMapper shipmentMapper;

    public ShipmentResponseDTO cancelShipmentById(Long id) {
        Shipment shipmentTemp = shipmentRepository.getShipmentById(id).orElseThrow();
        shipmentTemp.setStatus("cancelado");
        shipmentRepository.save(shipmentTemp);
        return shipmentMapper.convertToDTO(shipmentTemp);
    }
}
