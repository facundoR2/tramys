package tramys.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tramys.DTOS.DireccionDTO;
import tramys.Services.OpenStreetMapService;

import java.util.List;

@RestController
@RequestMapping("/api/mapas")
public class OpenStreetMapController {
    private final OpenStreetMapService osmService;

    @Autowired
    public OpenStreetMapController(OpenStreetMapService osmService) {
        this.osmService = osmService ;
    }

    @GetMapping("buscar")
    public ResponseEntity<List<DireccionDTO>> buscarDirecciones(
            @RequestParam String query
    ) {
        List<DireccionDTO> resultados = osmService.buscarDirecciones(query);
        if(resultados.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/location")
    public ResponseEntity<String> getLocation(@RequestParam String query){
        String locationData = osmService.getLocationData(query);
        return ResponseEntity.ok(locationData);
    }



}
