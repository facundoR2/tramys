package tramys.Services;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tramys.DTOS.PasoDTO;
import tramys.DTOS.TramiteDTO;
import tramys.Models.*;
import tramys.Repositorys.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class TramiteService {

    private final TramiteRepository tramiteRepo;
    private final TramitePasoRepository tramitePasoRepository;
    private final PasoRepository pasoRepository;

    private final RecintoRepository recintoRepository;
    private final UsuarioRepository usuarioRepo;


    public TramiteService(TramiteRepository tramiteRepo,
                          TramitePasoRepository tramitePasoRepository,
                          PasoRepository pasoRepository,
                          RecintoRepository recintoRepository,
                          UsuarioRepository usuarioRepo
                          ){
        this.tramiteRepo = tramiteRepo;
        this.tramitePasoRepository = tramitePasoRepository;
        this.pasoRepository = pasoRepository;
        this.recintoRepository = recintoRepository;
        this.usuarioRepo = usuarioRepo;
    }


    public List<Paso> convertirPasos(TramitePaso tramitePaso,List<PasoDTO> pasoDTOS){
        List<Paso> pasos = new ArrayList<>();
        for (PasoDTO pasoDto:pasoDTOS) {
            Paso Npaso = new Paso();
            Npaso.setNombre(pasoDto.getNombre());
            Npaso.setDescripcion(pasoDto.getDescripcion());
            Npaso.setTramitePasos(tramitePaso);
            Npaso.setOrden(pasoDto.getOrden());
            pasos.add(Npaso);

        }
        return pasos;
    }

    @Transactional
    public void RegistrarTramite(TramiteDTO tramiteDTO){
        Usuario Nusuario = new Usuario();
        Tramite tramite = new Tramite();
        TramitePaso tramitePaso = new TramitePaso();
        //validacion de usuario.

        Optional<Usuario> usuarioExistente = usuarioRepo.findByCorreo(tramiteDTO.getUsuario());
        if (usuarioExistente.isPresent()){
            Nusuario = usuarioExistente.get();
        }else {
            throw new IllegalArgumentException("el Username proporcionado no se ha encontrado");
        }

        //validaciones para el tramite.

        if(tramiteDTO.getNombreRecinto().isEmpty() || tramiteDTO.getNombre().isEmpty()){
            throw new IllegalArgumentException("EL nombre del recinto o del tramite no pueden estar vacios");

        }
        if (tramiteDTO.getPasos().isEmpty()){
            throw new IllegalArgumentException("El tramite Debe contener almenos 1 paso, por favor revise el formulario");

        }
        //guardamos el tramite

        tramite.setNombre(tramiteDTO.getNombre());
        tramite.setEstado(TramiteEstado.ACTIVO);
        tramite.setUsuario(Nusuario);
        tramiteRepo.save(tramite);

        //convertimos los pasos y los guardamos.
        List<Paso> pasos = convertirPasos(tramitePaso,tramiteDTO.getPasos());
        pasoRepository.saveAll(pasos);
        //paso 1: buscamos el recinto por el nombre
        Optional<Recinto> existente = recintoRepository.findBynombre(tramiteDTO.getNombreRecinto());
        if(existente.isPresent()){
            Recinto Nrecinto = existente.get();
            tramite.setRecinto(Nrecinto);
        }
        //guardamos la relacion en tramitePasos.
        for (Paso paso:pasos) {
            tramitePaso.setTramite(tramite);
            tramitePaso.setOrden(paso.getOrden());
            tramitePaso.setPaso(paso);
            tramitePasoRepository.save(tramitePaso);
        }
        //agregar para documentos.(con validacion de si existe un link o no).
    }
}
