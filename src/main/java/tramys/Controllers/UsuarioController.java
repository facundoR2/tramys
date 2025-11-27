package tramys.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tramys.DTOS.CrearUsuarioDTO;
import tramys.DTOS.DarBajaUsuarioDTO;
import tramys.Models.Rol;
import tramys.Models.Usuario;
import tramys.Repositorys.UsuarioRepository;
import tramys.Services.UsuarioService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api") // aca se derivan las peticiones
public class UsuarioController {

    private final UsuarioService userService;

    public UsuarioController(UsuarioService userService){
        this.userService = userService;
    }


//    @GetMapping("/usuario/current")
//    public ResponseEntity<Object> getUsuarioActual() {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        if (principal instanceof UserDetails) {
//            String username = ((UserDetails) principal).getUsername(); //adquiere el correo del usuario actualmente logeado.
//            Usuario usuario = .findByCorreo(username).orElse(null);
//            if (usuario == null){
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("error: Usuario no encontrado"); //devuelve error de usuario no encontrado.
//            }
//            return new ResponseEntity<>(usuario,HttpStatus.OK);
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//
//        }
//
//
//    }

    @PostMapping("/usuario/crear")
    public ResponseEntity<?> crearUsuario(@RequestBody CrearUsuarioDTO crearUsuarioDTO){
        Map<String,Object> response = new HashMap<>();
        //crear validaciones
        if(crearUsuarioDTO.getNombre().isBlank() || crearUsuarioDTO.getCorreo().isEmpty()){
            response.put("success", false);
            response.put("message","El campo no puede estar vacio.");
            return ResponseEntity.badRequest().body(response);
        }else {
            try {
                userService.registrarUsuario(crearUsuarioDTO);
                response.put("success", true);
                response.put("message","Usuario registrado Correctamente");

                return ResponseEntity.ok().body(response);

            }catch (IllegalArgumentException a){
                response.put("error",true);
                response.put("message",a.getMessage());
                return ResponseEntity.internalServerError().body(response);
            }
        }
    }

    @PutMapping("/usuario/baja")
    public ResponseEntity<Object> bajarUsuario(@RequestBody DarBajaUsuarioDTO darBajaUsuarioDTO){
        Map<String,Object> response = new HashMap<>();
        userService.borrarLogicamente(darBajaUsuarioDTO);
       return ResponseEntity.ok().body(response);
    }



}

