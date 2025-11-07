package tramys.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import tramys.DTOS.BusquedaRecintoDTO;
import tramys.DTOS.CrearRecintoDTO;
import tramys.DTOS.RecintoDTO;
import tramys.Models.Recinto;
import tramys.Models.Usuario;
import tramys.Repositorys.RecintoRepository;
import tramys.Repositorys.UsuarioRepository;
import tramys.Services.RecintoService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/recinto")
public class RecintoController {

    @Autowired
    private RecintoRepository recintoRepository;

    @Autowired
    private RecintoService recintoService;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @GetMapping("/buscar")
    public List<BusquedaRecintoDTO> buscarRecinto(@RequestParam String nombre){
        return recintoService.buscarRecintosPorNombre(nombre);
    }







    @PostMapping("/nuevoRecinto")
    public ResponseEntity<Object> crearRecinto(@RequestBody CrearRecintoDTO crearRecintoDTO){
        if(Double.compare(crearRecintoDTO.getLat(), 0.0) == 0 || Double.compare(crearRecintoDTO.getLng(),0.0) == 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("los campos 'lat' y 'lng' no pueden ser vacios");
        }

        if(crearRecintoDTO.getNombre().isBlank()){
            return new ResponseEntity<>("El campo nombre no puede estar vacio", HttpStatus.BAD_REQUEST);
        }
        Recinto recinto = new Recinto(crearRecintoDTO.getLat(),crearRecintoDTO.getLng(),crearRecintoDTO.getNombre(),crearRecintoDTO.getDireccion(),crearRecintoDTO.gethAtencion(),crearRecintoDTO.gethCierre());
        recintoRepository.save(recinto);


        return new ResponseEntity<>("RECINTO creado correctamente",HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("los campos 'lat' y 'lng' no pueden ser nulos");
    }

    @GetMapping("/traerTanda")
    public List<RecintoDTO> getRecintosTanda(){
        return recintoService.obtenerRecintosAleatorios(5).stream()
                .map(recinto -> new RecintoDTO(
                        recinto.getLat(),
                        recinto.getLng(),
                        recinto.getNombre(),
                        recinto.getDireccion(),
                        recinto.gethAtencion(),
                        recinto.gethCierre()
                ))
                .collect(Collectors.toList());
    }

    //metodo para borrado logico.

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrarRecinto(@PathVariable int id, String usuario){

        Usuario user = usuarioRepository.findByCorreo(usuario).orElse(null);
        recintoService.borrarRecintoLogicamente(id,usuario);
        return ResponseEntity.ok("Recinto desactivado");
    }

}
