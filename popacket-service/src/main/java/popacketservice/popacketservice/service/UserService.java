package popacketservice.popacketservice.service;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import popacketservice.popacketservice.exception.ConflictException;
import popacketservice.popacketservice.mapper.UserMapper;
import popacketservice.popacketservice.model.dto.LoginRequestDTO;
import popacketservice.popacketservice.model.dto.UserRequestDTO;
import popacketservice.popacketservice.model.dto.UserResponseDTO;
import popacketservice.popacketservice.model.entity.User;
import popacketservice.popacketservice.repository.UserRepository;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
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

    public UserResponseDTO updateProfileUser(@NotNull UserRequestDTO user) {
        if (userRepository.existsByEmailOrDocument(user.getEmail(), user.getDocument())) {
            User userTemp = userRepository.findByDocument(user.getDocument());
            userTemp.setPhone(user.getPhone());
            userTemp.setEmail(user.getEmail());
            userTemp.setName(user.getName());
            userTemp.setPass(user.getPass());
            userRepository.save(userTemp);
            return userMapper.convertToDTO(userTemp);
            } else {
            throw new ConflictException("El usuario no existe");
        }
    }
<<<<<<< HEAD

    //Configurar Perfil (metodos Privados)
    private UserResponseDTO updateUserName(@NotNull UserRequestDTO user) {
            User user1 = userRepository.findByDocument(user.getDocument());
            user1.setName(user.getName());
            userRepository.save(user1);
            return userMapper.convertToDTO(user1);
    }
    private UserResponseDTO updateUserLastName(@NotNull UserRequestDTO user) {
            User user1 = userRepository.findByDocument(user.getDocument());
            user1.setLastName(user.getLastName());
            userRepository.save(user1);
            return userMapper.convertToDTO(user1);
    }
    private UserResponseDTO updateUserPhone(@NotNull UserRequestDTO user) {
            User user1 = userRepository.findByDocument(user.getDocument());
            user1.setPhone(user.getPhone());
            userRepository.save(user1);
            return userMapper.convertToDTO(user1);
    }
    private UserResponseDTO updateUserEmail(@NotNull UserRequestDTO user) {
        User user1 = userRepository.findByDocument(user.getDocument());
        user1.setEmail(user.getEmail());
        userRepository.save(user1);
        return userMapper.convertToDTO(user1);
    }

    public UserResponseDTO Login(@NotNull LoginRequestDTO user) {
        boolean exists = userRepository.existsByEmail(user.getUsername());
        User userEntity = userRepository.findByEmail(user.getUsername());
        if (exists) {
            return userMapper.convertToDTO(userRepository.findByDocument(userEntity.getDocument()));
        }
        throw new ConflictException("El usuario no existe");
    }
=======
>>>>>>> feature/ConfigureProfile
}
