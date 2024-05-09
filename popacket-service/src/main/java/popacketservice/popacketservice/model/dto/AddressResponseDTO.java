package popacketservice.popacketservice.model.dto;

import jakarta.validation.constraints.NotBlank;

public class AddressResponseDTO {
    private Long id;
    private String address;
    private String departament;
    private String province;
    private String district;
}
