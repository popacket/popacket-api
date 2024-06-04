package popacketservice.popacketservice.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import popacketservice.popacketservice.mapper.PackageMapper;
import popacketservice.popacketservice.model.dto.PackageResponseDTO;
import popacketservice.popacketservice.model.entity.Package;
import popacketservice.popacketservice.repository.PackageRepository;

import java.util.List;

@Service
@Data
@NoArgsConstructor
@AllArgsConstructor

public class PackageService {
    public PackageRepository packageRepository;
    public PackageMapper packageMapper;

    public List<PackageResponseDTO> getAllPackageBySenderId(Long senderId) {
        List<Package> packages = packageRepository.findAllByOrderBySenderId(senderId).orElseThrow();
        //En esta linea conviertes la lista formato entity a DTO
        List<PackageResponseDTO> paquetesDTO = packageMapper.convertToListDTO(packages);
        return paquetesDTO;
    }
}
