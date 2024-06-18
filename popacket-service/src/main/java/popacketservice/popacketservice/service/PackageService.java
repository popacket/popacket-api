package popacketservice.popacketservice.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import popacketservice.popacketservice.exception.ResourceNotFoundException;
import popacketservice.popacketservice.mapper.PackageMapper;
import popacketservice.popacketservice.model.dto.PackageRequestDTO;
import popacketservice.popacketservice.model.dto.PackageResponseDTO;
import popacketservice.popacketservice.model.entity.Package;
import popacketservice.popacketservice.model.entity.User;
import popacketservice.popacketservice.repository.PackageRepository;
import popacketservice.popacketservice.repository.UserRepository;

import java.time.LocalDate;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackageService {
    @Autowired
    private PackageRepository packageRepository;
    private PackageMapper packageMapper;
    private UserRepository userRepository;

    public PackageResponseDTO createPackage(PackageRequestDTO packageRequestDTO) {
        User userSender = userRepository.findById(packageRequestDTO.getSenderId())
                .orElseThrow(()-> new ResourceNotFoundException("La cuenta de envio no fue encontrada"));
        User userRecipient =  userRepository.findById(packageRequestDTO.getRecipientId())
                .orElseThrow(()-> new ResourceNotFoundException("La cuenta de destino no fue encontrada"));
        Package packageE = packageMapper.convertToEntity(packageRequestDTO);
        packageE.setCreatedAt(LocalDate.now());
        packageE.setSender(userSender);
        packageE.setRecipient(userRecipient);
        packageRepository.save(packageE);
        return packageMapper.convertToDTO(packageE);
    }
}
