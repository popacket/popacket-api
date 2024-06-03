package popacketservice.popacketservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import popacketservice.popacketservice.exception.ConflictException;
import popacketservice.popacketservice.mapper.UserMapper;
import popacketservice.popacketservice.model.dto.UserRequestDTO;
import popacketservice.popacketservice.model.dto.UserResponseDTO;
import popacketservice.popacketservice.model.entity.User;
import popacketservice.popacketservice.repository.UserRepository;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    //@Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = userMapper.convertToEntity(userRequestDTO);

        boolean findUser = userRepository.existsByEmailOrDocument(userRequestDTO.getEmail(), userRequestDTO.getDocument());
        if (!findUser) {
            user.setCreateAt(LocalDate.now());
            User savedUser = userRepository.save(user);
            return userMapper.convertToDTO(savedUser);
        } else {
            throw new ConflictException("Un usuario ya se encuentra registrado con estos datos");
        }
    }
}
