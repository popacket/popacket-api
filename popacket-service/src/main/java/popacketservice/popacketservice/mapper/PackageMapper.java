package popacketservice.popacketservice.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import popacketservice.popacketservice.model.dto.PackageRequestDTO;
import popacketservice.popacketservice.model.dto.PackageResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor

public class PackageMapper {
    private final ModelMapper modelMapper;

    public Package convertToEntity(PackageRequestDTO packageRequestDTO) {
        return modelMapper.map(packageRequestDTO, Package.class);
    }

    public PackageResponseDTO convertToDto(Package packageEntity) {
        return modelMapper.map(packageEntity, PackageResponseDTO.class);
    }

    public List<PackageResponseDTO> convertToDtoList(List<Package> packages) {
        return packages.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
     }
    }
