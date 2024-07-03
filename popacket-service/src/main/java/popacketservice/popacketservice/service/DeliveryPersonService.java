package popacketservice.popacketservice.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import popacketservice.popacketservice.mapper.DeliveryPersonMapper;
import popacketservice.popacketservice.model.dto.DeliveryPersonRequestDTO;
import popacketservice.popacketservice.model.dto.DeliveryPersonResponseDTO;
import popacketservice.popacketservice.model.entity.DeliveryPerson;
import popacketservice.popacketservice.repository.DeliveryPersonRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class DeliveryPersonService {
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
    }
}
