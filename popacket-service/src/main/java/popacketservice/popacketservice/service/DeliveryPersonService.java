package popacketservice.popacketservice.service;

import lombok.AllArgsConstructor;
<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
=======
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import popacketservice.popacketservice.exception.ConflictException;
>>>>>>> develop
import popacketservice.popacketservice.mapper.DeliveryPersonMapper;
import popacketservice.popacketservice.model.dto.DeliveryPersonRequestDTO;
import popacketservice.popacketservice.model.dto.DeliveryPersonResponseDTO;
import popacketservice.popacketservice.model.entity.DeliveryPerson;
import popacketservice.popacketservice.repository.DeliveryPersonRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class DeliveryPersonService {
<<<<<<< HEAD
    @Autowired
    private DeliveryPersonRepository deliveryPersonRepository;
    @Autowired
    private DeliveryPersonMapper deliveryPersonMapper;

    public DeliveryPersonResponseDTO registerDeliveryPerson(DeliveryPersonRequestDTO deliveryPersonRequestDTO) {
        if (deliveryPersonRepository.existsByNameAndPhone(
                deliveryPersonRequestDTO.getName(),
                deliveryPersonRequestDTO.getPhone())){
            DeliveryPerson deliveryPerson = deliveryPersonRepository
                    .getDeliveryPersonByPhoneAndName(deliveryPersonRequestDTO.getName(),
                            deliveryPersonRequestDTO.getPhone()).orElseThrow(() -> new RuntimeException("El deliveryPerson no fue encontrado"));
            return deliveryPersonMapper.convertToDTO(deliveryPerson);
        } else {
            DeliveryPerson delveryPerson1 = new DeliveryPerson();
            delveryPerson1.setName(deliveryPersonRequestDTO.getName());
            delveryPerson1.setPhone(deliveryPersonRequestDTO.getPhone());
            delveryPerson1.setType(deliveryPersonRequestDTO.getType());
            return deliveryPersonMapper.convertToDTO(deliveryPersonRepository.save(delveryPerson1));
        }
    }
    public List<DeliveryPersonResponseDTO> getAllDeliveryPerson(){
        return deliveryPersonMapper.convertToListDTO(deliveryPersonRepository.findAll());
=======

    @Autowired
    private final DeliveryPersonRepository deliveryPersonRepository;
    private final DeliveryPersonMapper deliveryPersonMapper;

    public DeliveryPersonResponseDTO RegisterDeliveryPerson(DeliveryPersonRequestDTO deliveryPersonRequestDTO) {
        DeliveryPerson deliveryPerson = deliveryPersonMapper.convertToEntity(deliveryPersonRequestDTO);

        boolean FindDeliveryPerson = deliveryPersonRepository.existsByNameAndPhone(deliveryPerson.getName(), deliveryPerson.getPhone());
        if (!FindDeliveryPerson) {
            DeliveryPerson savedDeliveryPerson = deliveryPersonRepository.save(deliveryPerson);
            return deliveryPersonMapper.convertToDTO(savedDeliveryPerson);
        }else {
            throw new ConflictException("El Delivery Person Ya existe en el sistema");
        }
    }

    public List<DeliveryPerson> getAllDeliveryPersons() {
        return deliveryPersonRepository.findAll();
    }

    public String deleteDeliveryPersonById(Long deliveryPersonId) {
        if(deliveryPersonRepository.findByIdDeliveryPerson(deliveryPersonId)){
            deliveryPersonRepository.deleteById(deliveryPersonId);
            return "DeliveryPerson Elimina Satgisfactoriqa mente";
        } else {
            return "No existe el deliveryPerson";
        }
>>>>>>> develop
    }
}
