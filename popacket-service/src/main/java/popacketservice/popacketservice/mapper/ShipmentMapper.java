package popacketservice.popacketservice.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import popacketservice.popacketservice.model.dto.ShipmentRequestDTO;
import popacketservice.popacketservice.model.dto.ShipmentResponseDTO;
import popacketservice.popacketservice.model.entity.Shipment;

import java.util.List;

@Component
@AllArgsConstructor
public class ShipmentMapper {

    private final ModelMapper modelMapper;

    public Shipment convertToEntity(ShipmentRequestDTO shipmentRequestDTO) {
        Shipment shipment = modelMapper.map(shipmentRequestDTO, Shipment.class);
        shipment.setReturnRequested("no solicitado");
        return shipment;
    }

    public ShipmentResponseDTO convertToDTO(Shipment shipment) {
        return modelMapper.map(shipment, ShipmentResponseDTO.class);
    }

    public List<ShipmentResponseDTO> convertToListDTO(List<Shipment> shipments) {
        return shipments.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
