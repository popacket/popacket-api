package popacketservice.popacketservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String document;
    private String name;
    private String lastName;
    private String email;
    private String pass;
    private String phone;
    private String address;
}
