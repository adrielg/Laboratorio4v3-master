package dam.isi.frsf.utn.edu.ar.laboratorio4v2.modelo;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mdominguez on 22/09/16.
 */
public class Usuario implements Serializable {

    private Integer id;
    private String nombre;
    private String correo;

    private Uri Ringstone;
    private List<Reserva> reservas;
    private Integer puntosSuperPremio;

    /*-ACA- Constructor de un Usuario*/
    public Usuario(String nombre,String correo,Uri Ringstone){
        this.correo=correo;
        this.nombre = nombre;
        this.Ringstone = Ringstone;
        reservas = new ArrayList<Reserva>();
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

    public void setNombre (String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Uri getRignton() {
        return Ringstone;
    }

    public void setRingstone(Uri ringstone) {
        Ringstone = ringstone;
    }

    public Uri getRingstone() {return Ringstone; }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public Integer getPuntosSuperPremio() {
        return puntosSuperPremio;
    }

    public void setPuntosSuperPremio(Integer puntosSuperPremio) {
        this.puntosSuperPremio = puntosSuperPremio;
    }
}
