package popacketservice.popacketservice.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import popacketservice.popacketservice.model.dto.UserRequestDTO;
import popacketservice.popacketservice.model.dto.UserResponseDTO;
import org.modelmapper.ModelMapper;
import popacketservice.popacketservice.model.entity.User;

import java.util.List;

@Component
@AllArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;

    public User convertToEntity(UserRequestDTO accountRequestDTO) {
        return modelMapper.map(accountRequestDTO, User.class);
    }

    public UserResponseDTO convertToDTO(User account) {
        return modelMapper.map(account, UserResponseDTO.class);
    }

    public List<UserResponseDTO> convertToListDTO(List<User> accounts) {
        return accounts.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
