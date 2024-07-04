package popacketservice.popacketservice.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import popacketservice.popacketservice.exception.ConflictException;
import popacketservice.popacketservice.exception.ResourceNotFoundException;
import popacketservice.popacketservice.mapper.ShipmentMapper;
import popacketservice.popacketservice.model.dto.RescheduleShipmentDTO;
import popacketservice.popacketservice.model.dto.ShipmentRatingDTO;
import popacketservice.popacketservice.model.dto.ShipmentRequestDTO;
import popacketservice.popacketservice.model.dto.ShipmentResponseDTO;

import popacketservice.popacketservice.model.entity.*;
import popacketservice.popacketservice.model.entity.Package;
import popacketservice.popacketservice.model.entity.Location;
import popacketservice.popacketservice.repository.*;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.time.LocalDateTime;

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

    public ShipmentResponseDTO cancelShipmentById(Long id) {
        if(shipmentRepository.ifExistsByPackageID(id)){
           Shipment shipmentTemp = shipmentRepository.getShipmentByPackageId(id).orElseThrow();
           shipmentTemp.setStatus("cancelado");
           Package pack = packageRepository.findById(id).orElseThrow();
           pack.setStatus("cancelado");
           shipmentTemp.setPackageEntity(pack);
           shipmentRepository.save(shipmentTemp);
           return shipmentMapper.convertToDTO(shipmentTemp);
        } else {
            throw new ConflictException("El package no existe con el id " + id);
        }
    }

    public Double getShipmentCost(Double weight, String serviceType) {
        BigDecimal priceBase = shipmentRateRepository.getBasePrice(BigDecimal.valueOf(weight), serviceType);
        BigDecimal pricePerKilometer = shipmentRateRepository.getPricePerKilometer(BigDecimal.valueOf(weight), serviceType);
        return pricePerKilometer.multiply(BigDecimal.valueOf(weight)).add(priceBase).doubleValue();
    }

    public ShipmentResponseDTO getShipmentById(Long id) {
        Shipment shipmentTemp = shipmentRepository.getShipmentById(id).orElseThrow();
        return shipmentMapper.convertToDTO(shipmentTemp);
    }

    public Object[] getStatusShipmentById(Long id) {
        return shipmentRepository.getStatusShipmentByIdOb(id).orElseThrow();
    }

    public ShipmentResponseDTO makeShipment(ShipmentRequestDTO shipmentRequestDTO){
        boolean resp = shipmentRepository.ifExistsByPackageID(shipmentRequestDTO.getPackageId());
        if(resp){
            throw new ConflictException("El envio ya se encuentra registrado");
        } else {
            Location destinationLocation = locationRepository.getLocationByAddress(shipmentRequestDTO.getDestinationLocationAddress())
                    .orElseThrow(() -> new RuntimeException("Destino no encontrado"));
            Location originLocation = locationRepository.getLocationByAddress(shipmentRequestDTO.getOriginLocationAddress())
                    .orElseThrow(() -> new RuntimeException("Origen no encontrado"));
            Package pack = packageRepository.findById(shipmentRequestDTO.getPackageId())
                    .orElseThrow(() -> new RuntimeException("Paquete no encontrado"));
            DeliveryPerson deliveryPerson = deliveryPersonRepository.findById(shipmentRequestDTO.getDeliveryPersonId())
                    .orElseThrow(() -> new RuntimeException("Persona de entrega no encontrada"));
            ShippingRate shippingRate = shipmentRateRepository.findByServiceTypeAndWeight(pack.getPaymentType(),pack.getWeight());
            Shipment shipment = shipmentMapper.convertToEntity(shipmentRequestDTO);
            shipment.setDestinationLocation(destinationLocation);
            shipment.setOriginLocation(originLocation);
            shipment.setPackageEntity(pack);
            shipment.setDeliveryPerson(deliveryPerson);
            shipment.setPickupDateTime(LocalDateTime.now());
            shipment.setDeliveryDateTime(LocalDateTime.now().plusDays(3));
            shipment.setShippingRate(shippingRate);
            Shipment savedShipment = shipmentRepository.save(shipment);
            return shipmentMapper.convertToDTO(savedShipment);}
    }

    public ShipmentResponseDTO updateScheduleShipment(ShipmentRequestDTO shipmentRequestDTO) {
        Shipment shipment = shipmentMapper.convertToEntity(shipmentRequestDTO);
        Shipment shipmentTemp = shipmentRepository.getShipmentById(shipment.getId()).orElseThrow();
        shipmentTemp.setPickupDateTime(shipment.getPickupDateTime());
        shipmentTemp.setDeliveryDateTime(shipment.getPickupDateTime().plusDays(3));
        shipmentRepository.save(shipmentTemp);
        return shipmentMapper.convertToDTO(shipmentTemp);
    }

    public ShipmentResponseDTO rateShipment(ShipmentRatingDTO ratingDto) {
        Shipment shipment = shipmentRepository.findById(ratingDto.getShipmentId())
                .orElseThrow(() -> new NoSuchElementException("Envío no encontrado con id: " + ratingDto.getShipmentId()));
        if (ratingDto.getRating() < 1 || ratingDto.getRating() > 10) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5");
        }
        shipment.setRating(ratingDto.getRating());
        shipment.setComments(ratingDto.getComments());
        shipmentRepository.save(shipment);
        return shipmentMapper.convertToDTO(shipment);
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

    @Transactional
    public ShipmentResponseDTO updateShipmentDestination(Long shipmentId, Long newDestinationId) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Envío no encontrado con ID: " + shipmentId));

        Location newDestination = locationRepository.findById(newDestinationId)
                .orElseThrow(() -> new ResourceNotFoundException("Ubicación de destino no encontrada con ID: " + newDestinationId));

        shipment.setDestinationLocation(newDestination);
        Shipment updatedShipment = shipmentRepository.save(shipment);

        return shipmentMapper.convertToDTO(updatedShipment);
    }
}
