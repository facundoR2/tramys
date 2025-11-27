package tramys.Repositorys;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tramys.Models.Recinto;


import java.util.List;
import java.util.Optional;

public interface RecintoRepository extends JpaRepository<Recinto,Integer> {

    Optional<Recinto> findBynombre(String nombre);

    @Override
    Optional<Recinto> findById(Integer integer);

    @Query("SELECT r FROM Recinto r ORDER BY FUNCTION('RAND')") //usamos funcion Rand de MYSQL
    List<Recinto> findRandomRecintos();

    List<Recinto> findBynombreContainingIgnoreCase(String nombre);


    @Modifying
    @Transactional
    @Query("UPDATE Recinto u SET u.activo = false WHERE u.id = :id")
    void desactivarRecinto(@Param("id") int id);
}
