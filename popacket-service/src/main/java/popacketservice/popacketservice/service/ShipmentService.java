package popacketservice.popacketservice.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import popacketservice.popacketservice.mapper.PaymentMapper;
import popacketservice.popacketservice.model.dto.AddressRequestDTO;
import popacketservice.popacketservice.model.dto.PackageRequestDTO;
import popacketservice.popacketservice.model.dto.PaymentResponseDTO;
import popacketservice.popacketservice.model.entity.Address;
import popacketservice.popacketservice.model.entity.Payment;

import java.math.BigDecimal;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ShipmentService {

    private final double cuota = 0.10;
    private final double IGV = 0.16;

    private GoogleMapsService googleMapsService;
    private PaymentMapper paymentMapper;
    /**, AddressRequestDTO originAddress, AddressRequestDTO destinationAddress*/
    public PaymentResponseDTO getShippingCost(PackageRequestDTO packageRequestDTO){
       // String origin = originAddress.getAddress() + "," + originAddress.getDistrict()+ "," + originAddress.getProvince() + "," + originAddress.getDepartament();
        //String destination = destinationAddress.getAddress() + "," + destinationAddress.getDistrict()+ "," + destinationAddress.getProvince() + "," + destinationAddress.getDepartament();
        //Address origenAd = getAddressWithLatLon(origin);
        //Address destinationAd = getAddressWithLatLon(destination);
       // double distance = getDistance(origenAd, destinationAd);
        double weigth = packageRequestDTO.getWeight();
        double height = packageRequestDTO.getHeight();
        double width = packageRequestDTO.getWidth();
        double depth = packageRequestDTO.getDepth();

        double price = cuota*weigth + cuota*height + cuota*width + cuota*depth;
        price = price + price*IGV;
        Payment payment = new Payment();
        payment.setPrice(BigDecimal.valueOf(price));
        payment.setId(Long.valueOf("45"));
        PaymentResponseDTO paymentD = paymentMapper.convertToDto(payment);
        return paymentD;
    }

    private Address getAddressWithLatLon(String addr){
        Address address = new Address();
        double[] lan_lon = googleMapsService.getLatitudLongitud(addr);
        address.setLatitud(lan_lon[0]);
        address.setLongitud(lan_lon[1]);
        return address;
    }

    private double getDistance(Address origin, Address destination){
        double latitudOrigen = origin.getLatitud();
        double longitudOrigen = origin.getLongitud();

        double latitudDestino = destination.getLatitud();
        double longitudDestino = destination.getLongitud();

        double distance = 2 * 6371 * Math.asin(Math.sqrt(Math.sin((latitudDestino - latitudOrigen) / 2) * Math.sin((latitudDestino - latitudOrigen) / 2) +
                Math.cos(latitudOrigen) * Math.cos(latitudDestino) * Math.sin((longitudDestino - longitudOrigen) / 2) * Math.sin((longitudDestino - longitudOrigen) / 2)));

        return distance;
    }
}
