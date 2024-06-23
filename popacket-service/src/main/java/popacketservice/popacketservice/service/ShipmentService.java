package popacketservice.popacketservice.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import popacketservice.popacketservice.exception.ConflictException;
import popacketservice.popacketservice.mapper.ShipmentMapper;
import popacketservice.popacketservice.model.dto.ShipmentRequestDTO;
import popacketservice.popacketservice.model.dto.ShipmentResponseDTO;

import popacketservice.popacketservice.model.entity.*;
import popacketservice.popacketservice.model.entity.Package;
import popacketservice.popacketservice.repository.*;

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
        Shipment shipmentTemp = shipmentRepository.getShipmentById(id).orElseThrow(
                () -> new RuntimeException("Envio no encontrado con el id ingresado" + id)
        );
        shipmentTemp.setStatus("cancelado");
        shipmentRepository.save(shipmentTemp);
        return shipmentMapper.convertToDTO(shipmentTemp);
    }
}
