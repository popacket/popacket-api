package popacketservice.popacketservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleMapsService {

    private final String API_KEY = "AIzaSyC8emyyoNuN1uFw4OK2x-dvX8ziV1yurZs";

    public double[] getLatitudLongitud(String direccion) {
        direccion = direccion.replace(" ", "+");
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + direccion + "&key=" + API_KEY;
        RestTemplate restTemplate = new RestTemplate();
        String jsonResponse = restTemplate.getForObject(url, String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            JsonNode resultsNode = jsonNode.get("results");
            if (resultsNode != null && resultsNode.isArray() && resultsNode.size() > 0) {
                JsonNode geometryNode = resultsNode.get(0).get("geometry");
                if (geometryNode != null) {
                    JsonNode locationNode = geometryNode.get("location");
                    if (locationNode != null) {
                        double latitud = locationNode.get("lat").asDouble();
                        double longitud = locationNode.get("lng").asDouble();
                        double[] lan_lon = new double[2];
                        lan_lon[0] = latitud;
                        lan_lon[1] = longitud;
                        return lan_lon;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}