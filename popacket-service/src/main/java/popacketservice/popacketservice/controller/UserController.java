package popacketservice.popacketservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import popacketservice.popacketservice.model.dto.UserPreferencesDTO;
import popacketservice.popacketservice.model.dto.UserRequestDTO;
import popacketservice.popacketservice.model.dto.UserResponseDTO;
import popacketservice.popacketservice.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Validated @RequestBody UserRequestDTO userDTO){
        UserResponseDTO createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    @PostMapping("/configure_user/{type}")
    public ResponseEntity<UserResponseDTO> updateUser(@Validated @RequestBody UserRequestDTO userDTO,@PathVariable("type") String type){
       UserResponseDTO updateUser = userService.updateProfileUser(userDTO,type);
       return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @PutMapping("/preferences/{userId}")
    public ResponseEntity<Map<String, Object>> updatePreferences(@PathVariable Long userId, @RequestBody UserPreferencesDTO preferencesDto) {
        userService.updatePreferences(userId, preferencesDto);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Preferencias actualizadas correctamente.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
