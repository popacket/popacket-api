package popacketservice.popacketservice.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import popacketservice.popacketservice.exception.ConflictException;
import popacketservice.popacketservice.mapper.ShipmentMapper;
import popacketservice.popacketservice.model.dto.RescheduleShipmentDTO;
import popacketservice.popacketservice.model.dto.ShipmentRequestDTO;
import popacketservice.popacketservice.model.dto.ShipmentResponseDTO;
import popacketservice.popacketservice.model.entity.*;
import popacketservice.popacketservice.model.entity.Package;
import popacketservice.popacketservice.repository.*;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

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

    private PackageRepository packageRepository;

    private UserRepository userRepository;

    private LocationRepository locationRepository;

    private DeliveryPersonRepository deliveryPersonRepository;

    public ShipmentResponseDTO cancelShipmentById(Long id) {
        Shipment shipmentTemp = shipmentRepository.getShipmentById(id).orElseThrow(
                () -> new RuntimeException("Envio no encontrado con el id ingresado" + id)
        );
        shipmentTemp.setStatus("cancelado");
        shipmentRepository.save(shipmentTemp);
        return shipmentMapper.convertToDTO(shipmentTemp);
    }

    public Double getShipmentCost(Double weight, String serviceType) {
        BigDecimal priceBase = shipmentRateRepository.getBasePrice(BigDecimal.valueOf(weight), serviceType);
        BigDecimal pricePerKilometer = shipmentRateRepository.getPricePerKilometer(BigDecimal.valueOf(weight), serviceType);
        Double price = priceBase.add(pricePerKilometer).doubleValue();
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

    public ShipmentResponseDTO rescheduleShipment(RescheduleShipmentDTO rescheduleDTO) {
        Shipment shipment = shipmentRepository.findById(rescheduleDTO.getPackageId())
                .orElseThrow(() -> new NoSuchElementException("Envío no encontrado con id: " + rescheduleDTO.getPackageId()));

        if (rescheduleDTO.getDeliveryDateTime().isBefore(rescheduleDTO.getPickupDateTime())) {
            throw new IllegalArgumentException("La fecha de entrega no puede ser anterior a la fecha de recogida.");
        }

        shipment.setPickupDateTime(rescheduleDTO.getPickupDateTime());
        shipment.setDeliveryDateTime(rescheduleDTO.getDeliveryDateTime());
        shipmentRepository.save(shipment);

        return shipmentMapper.convertToDTO(shipment);
    }

}
