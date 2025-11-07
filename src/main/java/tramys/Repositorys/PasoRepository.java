package tramys.Repositorys;


import org.springframework.data.jpa.repository.JpaRepository;
import tramys.Models.Paso;

public interface PasoRepository extends JpaRepository<Paso,Integer> {

    Paso findBynombre (String nombre);
}
