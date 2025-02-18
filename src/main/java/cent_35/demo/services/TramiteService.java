package cent_35.demo.services;

import cent_35.demo.Dtos.PasoDTO;
import cent_35.demo.Dtos.TramiteDTO;
import cent_35.demo.Models.Tramite;
import cent_35.demo.Models.TramitePaso;
import cent_35.demo.repositorys.RecintoRepository;
import cent_35.demo.repositorys.TramitePasoRepository;
import cent_35.demo.repositorys.TramiteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class TramiteService {

    private final TramiteRepository tramiteRepository;

    private final TramitePasoRepository tramitePasoRepository;


    public TramiteService(TramiteRepository tramiteRepository, TramitePasoRepository tramitePasoRepository){
        this.tramiteRepository = tramiteRepository;
        this.tramitePasoRepository = tramitePasoRepository;
    }

    public List<TramiteDTO> obtenerTramitesPorNombreRecinto(String nombreRecinto){
        List<Tramite> tramites = tramiteRepository.findByRecintoNombre(nombreRecinto);

        return tramites.stream()
                .map(tramite -> {
                    List<TramitePaso> tramitePasos = tramitePasoRepository.findByTramiteOrderByOrdenAsc(tramite);
                    List<PasoDTO> pasosDTO = tramitePasos.stream()
                            .map(tramitePaso -> new PasoDTO(tramitePaso.getPaso().getNombre(),tramitePaso.getPaso().getDescripcion()))
                            .collect(Collectors.toList());
                    return new TramiteDTO(
                            pasosDTO,
                            tramite.getNombre(),
                            tramite.getRecinto().getNombre()
                    );
                })
                .collect(Collectors.toList());

    }
}
