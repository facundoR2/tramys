package tramys.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tramys.DTOS.CrearUsuarioDTO;
import tramys.DTOS.DarBajaUsuarioDTO;
import tramys.DTOS.UsuarioDTO;
import tramys.Models.Rol;
import tramys.Models.Usuario;
import tramys.Repositorys.UsuarioRepository;

import java.util.Optional;

@Service
public class UsuarioService {


    private final  UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    public void borrarLogicamente(DarBajaUsuarioDTO dto){
        Usuario user = new Usuario();
        Optional<Usuario> existente = usuarioRepository.findByCorreo(dto.getCorreo());
        if(existente.isPresent()){
            user = existente.get();
        }
        user.setEstado(0); // pasamos el estado a cero (osea INACTIVO)
        usuarioRepository.save(user);
    }

//    public Usuario buscarUsuarioActual(UsuarioDTO usuarioDTO){
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        if (principal instanceof UserDetails) {
//            String username = ((UserDetails) principal).getUsername(); //adquiere el correo del usuario actualmente logeado.
//            Usuario usuario = usuarioRepository.findByCorreo(username).orElse(null);
////            if (usuario == null){
////                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("error: Usuario no encontrado"); //devuelve error de usuario no encontrado.
////            }
////            return new ResponseEntity<>(usuario,HttpStatus.OK);
////        } else {
////            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
////
////        }
//    }

    public boolean registrarUsuario (CrearUsuarioDTO dto){
        //validaciones y variables locales.
        if(dto.getCorreo().isEmpty() || dto.getPassword().isEmpty()){
            throw new IllegalArgumentException("el correo o la contraseña no pueden estar vacios.");
        }
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setEstado(1); //no instanciamos como ACTIVO.
        nuevoUsuario.setCorreo(dto.getCorreo());
        nuevoUsuario.setNombre(dto.getNombre());
        nuevoUsuario.setApellido(dto.getApellido());
        nuevoUsuario.setPassword(dto.getPassword());
        nuevoUsuario.setRol(dto.getRol());
        try{
            usuarioRepository.save(nuevoUsuario);

        }catch (Exception a){
            throw new IllegalArgumentException("Ocurrio un error al registrar el usuario en la base:",a.getCause());
        }

        return true;

    }
}
