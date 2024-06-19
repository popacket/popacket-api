package popacketservice.popacketservice.mapper;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;
import popacketservice.popacketservice.model.dto.PackageRequestDTO;
import popacketservice.popacketservice.model.dto.PackageResponseDTO;
import popacketservice.popacketservice.model.entity.Package;
import popacketservice.popacketservice.model.entity.User;
import popacketservice.popacketservice.repository.UserRepository;

import java.util.List;

@Component
public class PackageMapper {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public PackageMapper(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        configureModelMapper();
    }

    private void configureModelMapper() {
        Converter<Long, User> idToUserConverter = ctx ->
                ctx.getSource() == null ? null : userRepository.findById(ctx.getSource()).orElse(null);

        PropertyMap<PackageRequestDTO, Package> packageMap = new PropertyMap<PackageRequestDTO, Package>() {
            protected void configure() {
                using(idToUserConverter).map(source.getSenderId()).setSender(null);
                using(idToUserConverter).map(source.getRecipientId()).setRecipient(null);
                skip().setId(null);
            }
        };

        modelMapper.addMappings(packageMap);
    }

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
