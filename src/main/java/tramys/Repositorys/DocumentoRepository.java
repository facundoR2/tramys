package tramys.Repositorys;


import org.springframework.data.jpa.repository.JpaRepository;
import tramys.Models.Documento;

public interface DocumentoRepository extends JpaRepository<Documento,Integer> {

    Documento findBynombre(String nombre);
}
