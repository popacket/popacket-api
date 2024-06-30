package popacketservice.popacketservice.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import popacketservice.popacketservice.exception.ConflictException;
import popacketservice.popacketservice.mapper.ShipmentMapper;
import popacketservice.popacketservice.model.dto.ShipmentRatingDTO;
import popacketservice.popacketservice.model.dto.ShipmentRequestDTO;
import popacketservice.popacketservice.model.dto.ShipmentResponseDTO;

import popacketservice.popacketservice.model.entity.*;
import popacketservice.popacketservice.model.entity.Package;
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
        shipment.setRating(ratingDto.getRating());
        shipment.setComments(ratingDto.getComments());
        shipmentRepository.save(shipment);
        return shipmentMapper.convertToDTO(shipment);
    }
}
