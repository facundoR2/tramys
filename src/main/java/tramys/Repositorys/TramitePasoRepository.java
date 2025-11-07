package tramys.Repositorys;


import org.springframework.data.jpa.repository.JpaRepository;
import tramys.Models.TramitePaso;

import java.util.Optional;

public interface TramitePasoRepository extends JpaRepository<TramitePaso, Integer> {



    @Override
    Optional<TramitePaso> findById(Integer integer);

}
