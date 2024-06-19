package popacketservice.popacketservice.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import popacketservice.popacketservice.mapper.ShipmentMapper;
import popacketservice.popacketservice.model.dto.ShipmentRequestDTO;
import popacketservice.popacketservice.model.dto.ShipmentResponseDTO;
import popacketservice.popacketservice.model.entity.Shipment;
import popacketservice.popacketservice.repository.ShipmentRepository;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ShipmentService {
    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private ShipmentMapper shipmentMapper;

    public ShipmentResponseDTO cancelShipmentById(Long id) {
        Shipment shipmentTemp = shipmentRepository.getShipmentById(id).orElseThrow();
        shipmentTemp.setStatus("cancelado");
        shipmentRepository.save(shipmentTemp);
        return shipmentMapper.convertToDTO(shipmentTemp);
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
