package tramys.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tramys.Services.OpenStreetMapService;

@RestController
@RequestMapping("/api")
public class OpenStreetMapController {
    private final OpenStreetMapService openStreetMapService;

    @Autowired
    public OpenStreetMapController(OpenStreetMapService openStreetMapService) {
        this.openStreetMapService = openStreetMapService;
    }

    @GetMapping("/location")
    public ResponseEntity<String> getLocation(@RequestParam String query){
        String locationData = openStreetMapService.getLocationData(query);
        return ResponseEntity.ok(locationData);
    }



}
