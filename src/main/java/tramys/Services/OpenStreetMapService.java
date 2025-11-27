package tramys.Services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tramys.DTOS.DireccionDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OpenStreetMapService {


    private final RestTemplate restTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public OpenStreetMapService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }
    public String getLocationData(String query){
        String url = "https://nominatim.openstreetmap.org/search?q="+query+"&format=json&addressdetails=1";
        ResponseEntity<String> response = restTemplate.getForEntity(url,String.class);
        return response.getBody();
    }

    public List<DireccionDTO> buscarDirecciones(String query) {
        String url = UriComponentsBuilder.fromUriString("https://nominatim.openstreetmap.org/search")
                .queryParam("q", query)
                .queryParam("format", "json")
                .queryParam("addressdetails", 1)
                .queryParam("limit", 15)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "MiApp/1.0 (contacto@miempresa.com)");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return filtrarYconvertir(response.getBody());
    }

    private List<DireccionDTO> filtrarYconvertir(String json) {
        try {
            JsonNode resultados = mapper.readTree(json);
            List<DireccionDTO> lista = new ArrayList<>();

            for (JsonNode node : resultados) {
                String clazz = node.path("class").asText();
                String type = node.path("type").asText();

                boolean esValido = "building".equals(clazz) ||
                        "amenity".equals(clazz) ||
                        ("place".equals(clazz) && "house".equals(type)) ||
                        ("highway".equals(clazz) && type.contains("residential"));
                if (!esValido) continue;
                DireccionDTO dto = new DireccionDTO();
                dto.setNombre(node.path("display_name").asText());
                dto.setLat(node.path("lat").asText());
                dto.setLon(node.path("lon").asText());
                dto.setClase(clazz);
                dto.setTipo(type);

                lista.add(dto);
            }
            return lista;
        } catch (Exception e) {
            throw new RuntimeException("Error procesando la respuesta de Normatin", e);
        }
    }
}
