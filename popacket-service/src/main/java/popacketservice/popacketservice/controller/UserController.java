package popacketservice.popacketservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import popacketservice.popacketservice.exception.ConflictException;
import popacketservice.popacketservice.model.dto.LoginRequestDTO;
import popacketservice.popacketservice.model.dto.UserRequestDTO;
import popacketservice.popacketservice.model.dto.UserResponseDTO;
import popacketservice.popacketservice.service.UserService;

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
    @PostMapping("/configure_user")
    public ResponseEntity<UserResponseDTO> updateUser(@Validated @RequestBody UserRequestDTO userDTO){
       UserResponseDTO updateUser = userService.updateProfileUser(userDTO);
       return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@Validated @RequestBody LoginRequestDTO loginRequest) {
        try {
            UserResponseDTO user = userService.login(loginRequest);
            return ResponseEntity.ok(user);
        } catch (ConflictException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/get-user/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") Long id){
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }
}
