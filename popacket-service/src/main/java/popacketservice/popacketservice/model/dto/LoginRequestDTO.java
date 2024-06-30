package popacketservice.popacketservice.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {

    @NotNull
    @Email
    private String username;

    @NotNull
    private String password;
<<<<<<< HEAD
}
=======
}
>>>>>>> 14b26f18b04e6e52012f041ab3cb2ae21df27b96
