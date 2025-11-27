package tramys.DTOS;

import java.util.ArrayList;
import java.util.List;

public class TramiteDTO {
    private List<PasoDTO> pasos = new ArrayList<>();
    private String nombre;

    private String nombreRecinto;

    private String usuario; //username( el email)





    public List<PasoDTO> getPasos() {
        return pasos;
    }

    public void setPasos(List<PasoDTO> pasos) {
        this.pasos = pasos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreRecinto() {
        return nombreRecinto;
    }

    public void setNombreRecinto(String nombreRecinto) {
        this.nombreRecinto = nombreRecinto;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }
}
