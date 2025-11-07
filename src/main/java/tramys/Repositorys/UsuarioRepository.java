package tramys.Repositorys;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tramys.Models.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

    List<Usuario> findByApellido(String apellido);

    Usuario findById(int id);
    Optional<Usuario> findByCorreo (String correo);


}
