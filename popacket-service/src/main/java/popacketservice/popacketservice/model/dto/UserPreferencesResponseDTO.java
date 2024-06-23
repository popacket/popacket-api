package popacketservice.popacketservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserPreferencesResponseDTO {

        private String defaultShippingAddress;
        private String preferredPaymentMethod;
        private String preferredShippingType;
}
