package popacketservice.popacketservice.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import popacketservice.popacketservice.model.dto.PackageRequestDTO;
import popacketservice.popacketservice.model.dto.PackageResponseDTO;
import org.modelmapper.ModelMapper;

import java.util.List;

@Component
@AllArgsConstructor
public class PackageMapper {

    private final ModelMapper modelMapper;

    public Package convertToEntity(PackageRequestDTO packageRequestDTO) {
        return modelMapper.map(packageRequestDTO, Package.class);
    }

    public PackageResponseDTO convertToDTO(Package packageEntity) {
        return modelMapper.map(packageEntity, PackageResponseDTO.class);
    }

    public List<PackageResponseDTO> convertToListDTO(List<Package> packages) {
        return packages.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
