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
import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private PackageRepository packageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private DeliveryPersonRepository deliveryPersonRepository;

    public List<ShipmentResponseDTO> getAllShipmentsBySenderID(Long id) {
        List<Shipment> shipmentList = shipmentRepository.findAllShipmentBySenderId(id).orElseThrow();
        return shipmentMapper.convertToListDTO(shipmentList);
    }
}
