package popacketservice.popacketservice.service;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import popacketservice.popacketservice.exception.ConflictException;
import popacketservice.popacketservice.mapper.UserMapper;
import popacketservice.popacketservice.model.dto.UserPreferencesDTO;
import popacketservice.popacketservice.model.dto.UserRequestDTO;
import popacketservice.popacketservice.model.dto.UserResponseDTO;
import popacketservice.popacketservice.model.entity.User;
import popacketservice.popacketservice.repository.UserRepository;

import java.time.LocalDate;
import java.util.NoSuchElementException;

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

    public UserResponseDTO updateProfileUser(@NotNull UserRequestDTO user, String type) {
        if (userRepository.existsByEmailOrDocument(user.getEmail(), user.getDocument())) {
            switch (type) {
                case "name":
                    return updateUserName(user);
                case "lastName":
                    return updateUserLastName(user);
                case "phone":
                    return updateUserPhone(user);
                case "email":
                    return updateUserEmail(user);
            }
        } else {
         throw new ConflictException("El usuario no existe");
        }
        return userMapper.convertToDTO(userRepository.findByDocument(user.getDocument()));
    }

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

    public User updatePreferences(Long userId, UserPreferencesDTO preferences) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NoSuchElementException("Usuario no encontrado con ID: " + userId));
        user.setDefaultShippingAddress(preferences.getDefaultShippingAddress());
        user.setPreferredPaymentMethod(preferences.getPreferredPaymentMethod());
        user.setPreferredShippingType(preferences.getPreferredShippingType());
        return userRepository.save(user);
    }

}
