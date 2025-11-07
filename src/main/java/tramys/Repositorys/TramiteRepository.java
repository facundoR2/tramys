package tramys.Repositorys;


import org.springframework.data.jpa.repository.JpaRepository;
import tramys.Models.Tramite;

import java.util.Optional;

public interface TramiteRepository extends JpaRepository<Tramite,Integer> {

    @Override
    Optional<Tramite> findById(Integer integer);

    Tramite findByNombre(String nombre);
}
