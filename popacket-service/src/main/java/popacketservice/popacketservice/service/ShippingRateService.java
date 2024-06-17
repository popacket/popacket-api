package popacketservice.popacketservice.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import popacketservice.popacketservice.repository.ShippingRateRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ShippingRateService {
    @Autowired
    private ShippingRateRepository shippingRateRepository;

    public List<String> getAllShippingRate() {
        List<String> shipppingRate = shippingRateRepository.getAllShippingRate();
        //Creamos a un tipo de estructura hash para eliminar duplicados
        Set<String> shippingRateSet = new HashSet<>(shipppingRate);
        //Volvemos a transformar en una lista para enviar el formato esperado
        List<String> shippingRateList = new ArrayList<>(shippingRateSet);
        return shippingRateList;
    }
}
