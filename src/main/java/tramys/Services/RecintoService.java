package tramys.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tramys.DTOS.BusquedaRecintoDTO;
import tramys.DTOS.CrearRecintoDTO;
import tramys.Models.Recinto;
import tramys.Repositorys.RecintoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecintoService {

    @Autowired
    private RecintoRepository recintoRepository;



    public Recinto CrearRecinto(CrearRecintoDTO crearRecintoDTO){



        Recinto recinto = new Recinto(crearRecintoDTO.getLat(),crearRecintoDTO.getLng(),crearRecintoDTO.getNombre(),crearRecintoDTO.getDireccion(),crearRecintoDTO.gethAtencion(),crearRecintoDTO.gethCierre());
        recintoRepository.save(recinto);
        return recinto;
    }

    public List<Recinto> obtenerRecintosAleatorios(int cantidad){
        return recintoRepository.findRandomRecintos().stream().limit(cantidad).collect(Collectors.toList());
    }

    public List<BusquedaRecintoDTO> buscarRecintosPorNombre(String nombre){
        List<Recinto> recintos = recintoRepository.findBynombreContainingIgnoreCase(nombre);

        return recintos.stream().map(recinto ->new BusquedaRecintoDTO(recinto.getNombre(),recinto.getDireccion())).collect(Collectors.toList());
    }

    public void borrarRecintoLogicamente (int id,String usuario){
        recintoRepository.desactivarRecinto(id);
    }
}
