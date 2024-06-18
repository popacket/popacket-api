package popacketservice.popacketservice.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import popacketservice.popacketservice.exception.ConflictException;
import popacketservice.popacketservice.mapper.DeliveryPersonMapper;
import popacketservice.popacketservice.model.dto.DeliveryPersonRequestDTO;
import popacketservice.popacketservice.model.dto.DeliveryPersonResponseDTO;
import popacketservice.popacketservice.model.entity.DeliveryPerson;
import popacketservice.popacketservice.repository.DeliveryPersonRepository;

@Service
@AllArgsConstructor
public class DeliveryPersonService {

    @Autowired
    private final DeliveryPersonRepository deliveryPersonRepository;
    private final DeliveryPersonMapper deliveryPersonMapper;

    public DeliveryPersonResponseDTO RegisterDeliveryPerson(DeliveryPersonRequestDTO deliveryPersonRequestDTO) {
        DeliveryPerson deliveryPerson = deliveryPersonMapper.convertToEntity(deliveryPersonRequestDTO);

        boolean FindDeliveryPerson = deliveryPersonRepository.findByIdDeliveryPerson(deliveryPerson.getId());
        if (!FindDeliveryPerson) {
            DeliveryPerson savedDeliveryPerson = deliveryPersonRepository.save(deliveryPerson);
            return deliveryPersonMapper.convertToDTO(savedDeliveryPerson);
        }else {
            throw new ConflictException("El Delivery Person Ya existe en el sistema");
        }
    }
}
