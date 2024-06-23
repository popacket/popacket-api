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


@Service
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ShipmentService {
    @Autowired
    private ShipmentRepository shipmentRepository;
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

    public ShipmentResponseDTO makeShipment(ShipmentRequestDTO shipmentRequestDTO){

        boolean resp = shipmentRepository.ifExistsByPackageID(shipmentRequestDTO.getPackageId());
        if(resp){
            throw new ConflictException("El envio ya se encuentra registrado");
        } else {

            Location destinationLocation = locationRepository.findById(shipmentRequestDTO.getDestinationLocationId())
                    .orElseThrow(() -> new RuntimeException("Destino no encontrado"));
            Location originLocation = locationRepository.findById(shipmentRequestDTO.getOriginLocationId())
                    .orElseThrow(() -> new RuntimeException("Origen no encontrado"));
            Package pack = packageRepository.findById(shipmentRequestDTO.getPackageId())
                    .orElseThrow(() -> new RuntimeException("Paquete no encontrado"));
            DeliveryPerson deliveryPerson = deliveryPersonRepository.findById(shipmentRequestDTO.getDeliveryPersonId())
                    .orElseThrow(() -> new RuntimeException("Persona de entrega no encontrada"));

            Shipment shipment = shipmentMapper.convertToEntity(shipmentRequestDTO);
            shipment.setDestinationLocation(destinationLocation);
            shipment.setOriginLocation(originLocation);
            shipment.setPackageEntity(pack);
            shipment.setDeliveryPerson(deliveryPerson);

            Shipment savedShipment = shipmentRepository.save(shipment);

            return shipmentMapper.convertToDTO(savedShipment);}
    }

}
