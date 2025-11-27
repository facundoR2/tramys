package tramys.Models;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import tramys.DTOS.UsuarioDTO;


import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Tramite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int idTramite;

    private String nombre;

    private TramiteEstado estado;

    // union con pasos
    @OneToMany(mappedBy = "tramite", fetch = FetchType.EAGER)
    private Set<TramitePaso> tramitePasos = new HashSet<>();

    //union con Recinto.

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recinto",referencedColumnName = "idRecinto")
    private Recinto recinto;

    //union con usuario.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Usuario",referencedColumnName = "idUsuario")
    private Usuario usuario;

    public Tramite(){}

    public Tramite(String nombre, Recinto recinto, Usuario usuario){
        this.nombre = nombre;
        this.estado = TramiteEstado.ACTIVO;
        setRecinto(recinto);
        this.usuario = usuario;
    }

    public void setRecinto(Recinto recinto){
        this.recinto = recinto;
    }



    public void addTramitePaso(TramitePaso tramitePaso){
        tramitePaso.setTramite(this);
        tramitePasos.add(tramitePaso);
    }

    public Set<Paso> getPasos() { //buscar los pasos desde el tramite.
        return tramitePasos.stream().map(tramitePaso -> tramitePaso.getPaso()).collect(Collectors.toSet());
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setEstado(TramiteEstado estado) {
        this.estado = estado;
    }

    public int getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(int idTramite) {
        this.idTramite = idTramite;
    }

    public Set<TramitePaso> getTramitePasos() {
        return tramitePasos;
    }

    public TramiteEstado getEstado() {
        return estado;
    }

    public Recinto getRecinto() {
        return recinto;
    }

    public void setTramitePasos(Set<TramitePaso> tramitePasos) {
        this.tramitePasos = tramitePasos;
    }

}
