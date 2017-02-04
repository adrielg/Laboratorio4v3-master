package dam.isi.frsf.utn.edu.ar.laboratorio4v2.modelo;

import java.io.Serializable;

/**
 * Created by mdominguez on 22/09/16.
 */
public class Ciudad implements Serializable {

    private Integer id;
    private String nombre;
    private Double tasa;



    public Ciudad(){}

    public Ciudad(Integer id,String nombre,Double tasa){
        this.id = id;
        this.nombre = nombre;
        this.tasa=tasa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getTasa() {
        return tasa;
    }

    public void setTasa(Double tasa) {
        this.tasa = tasa;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ciudad)) return false;

        Ciudad ciudad = (Ciudad) o;

        if (getId() != null ? !getId().equals(ciudad.getId()) : ciudad.getId() != null)
            return false;
        if (getNombre() != null ? !getNombre().equals(ciudad.getNombre()) : ciudad.getNombre() != null)
            return false;
        return getTasa() != null ? getTasa().equals(ciudad.getTasa()) : ciudad.getTasa() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getNombre() != null ? getNombre().hashCode() : 0);
        result = 31 * result + (getTasa() != null ? getTasa().hashCode() : 0);
        return result;
    }

    public static final Ciudad[] CIUDADES ={
            new Ciudad(1,"Paris",0.12),
            new Ciudad(2,"New York",0.18),
            new Ciudad(3,"Berlin",0.03),
            new Ciudad(4,"Londres",0.09),
            new Ciudad(5,"Roma",0.14),
            new Ciudad(6,"Amsterdan",0.05)
    };


}
