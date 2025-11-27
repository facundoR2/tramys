package tramys.Models;



import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;



@Entity
public class TramitePaso {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Integer idTramitePaso;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tramite_id",referencedColumnName = "idTramite")
    private Tramite tramite;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="Paso",referencedColumnName = "idPaso")
    private Paso paso;

    private Integer orden; // el orden de los pasos

    public TramitePaso(){}

    public TramitePaso(Tramite tramite, Paso paso, Integer orden){
        this.tramite = tramite;
        this.paso = paso;
        this.orden = orden;
    }

    public Tramite getTramite() {
        return tramite;
    }

    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }

    public Paso getPaso() {
        return paso;
    }

    public void setPaso(Paso paso) {
        this.paso = paso;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getIdTramitePaso() {
        return idTramitePaso;
    }

    public void setIdTramitePaso(Integer idTramitePaso) {
        this.idTramitePaso = idTramitePaso;
    }
}
