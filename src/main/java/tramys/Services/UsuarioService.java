package tramys.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tramys.Repositorys.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    //public void borrarLogicamente(int id){usuarioRepository.desactivarUsuario(id);}
}
