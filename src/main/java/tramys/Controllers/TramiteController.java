package tramys.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tramys.DTOS.TramiteDTO;

import tramys.Services.TramiteService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TramiteController {
    private final TramiteService tramiteService;

    public  TramiteController (TramiteService tramiteService){
        this.tramiteService = tramiteService;

    }

    @PostMapping("/tramite/crear")
    public ResponseEntity<?> crearTramite(@RequestBody TramiteDTO tramiteDTO){
        Map<String, Object> response = new HashMap<>();
        try{
            tramiteService.RegistrarTramite(tramiteDTO);
            response.put("success", true);
            response.put("message", "Se a registrado el Tramite correctamente");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e){
            response.put("success", false);
            response.put("error",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }


    }
    @PostMapping("tramite/baja")
    public ResponseEntity<?> DardeBajaTramite(){
        //agregar logica para usar el id del tramite para
        // dar de baja el mismo, ademas de sus pasos.
        return ResponseEntity.ok().body("");
    }

    //falta crear baja_tramite, mostrar locaciones( para el mapa de index) y modificar tramite.
}
