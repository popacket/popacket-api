package popacketservice.popacketservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import popacketservice.popacketservice.exception.ResourceNotFoundException;
import popacketservice.popacketservice.mapper.PackageMapper;
import popacketservice.popacketservice.model.dto.PackageRequestDTO;
import popacketservice.popacketservice.model.dto.PackageResponseDTO;
import popacketservice.popacketservice.model.entity.Package;
import popacketservice.popacketservice.model.entity.User;
import popacketservice.popacketservice.repository.PackageRepository;
import popacketservice.popacketservice.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class PackageService {

    private final PackageRepository packageRepository;
    private final PackageMapper packageMapper;
    private final UserRepository userRepository;

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

    @Transactional(readOnly = true)
    public List<PackageResponseDTO> getPackagesBySender(Long senderId) {
        List<Package> packages = packageRepository.findBySenderId(senderId);
        return packageMapper.convertToListDTO(packages);
    }

    @Transactional(readOnly = true)
    public List<PackageResponseDTO> getPackagesByRecipient(Long recipientId) {
        List<Package> packages = packageRepository.findByRecipientId(recipientId);
        return packageMapper.convertToListDTO(packages);
    }
}
