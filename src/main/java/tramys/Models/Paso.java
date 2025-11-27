package tramys.Models;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;


import java.util.HashSet;
import java.util.Set;

@Entity
public class Paso {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Integer idPaso;

    private int orden;
    private String nombre;
    private String descripcion;



    @OneToMany(mappedBy = "paso", fetch = FetchType.EAGER)
    private Set<TramitePaso> tramitePasos = new HashSet<>();



    public  Paso(){}

    public  Paso(int orden,String nombre,String descripcion){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.orden = orden;
    }




    //GETTERS Y SETTERS


    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setTramitePasos(TramitePaso tramitePaso){
        tramitePaso.setPaso(this); //agregamos el paso
        tramitePasos.add(tramitePaso); //agregamos a la lista de tramitepasos el tramitepaso

    }



}
