package dam.isi.frsf.utn.edu.ar.laboratorio4v2.utils;

import java.io.Serializable;
import dam.isi.frsf.utn.edu.ar.laboratorio4v2.modelo.Ciudad;

public class FormBusqueda implements Serializable {
    private Double precioMinimo;
    private Double precioMaximo;
    private Ciudad ciudad;
    private Integer huespedes;
    private Boolean permiteFumar;

    /*------------------------------------- Constructor ------------------------------------------*/
    public FormBusqueda(){

    }

    /*------------------------------------- Constructor ------------------------------------------*/
    public FormBusqueda(Double precioMinimo, Double precioMaximo) {
        this(precioMinimo,precioMaximo,null,null,null);
    }

    /*------------------------------------- Constructor ------------------------------------------*/
    public FormBusqueda(Ciudad ciudad) {
        this(null,null,ciudad,null,null);
    }

    /*------------------------------------- Constructor ------------------------------------------*/
    public FormBusqueda(Double precioMinimo, Double precioMaximo, Ciudad ciudad, Integer huespedes, Boolean permiteFumar) {
        this.precioMinimo = precioMinimo;
        this.precioMaximo = precioMaximo;
        this.ciudad = ciudad;
        this.huespedes = huespedes;
        this.permiteFumar = permiteFumar;
    }

    /*------------------------------------- Gets y Sets ------------------------------------------*/
    public Double getPrecioMinimo() {
        return precioMinimo;
    }

    public void setPrecioMinimo(Double precioMinimo) {
        this.precioMinimo = precioMinimo;
    }

    public Double getPrecioMaximo() {
        return precioMaximo;
    }

    public void setPrecioMaximo(Double precioMaximo) {
        this.precioMaximo = precioMaximo;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public Integer getHuespedes() {
        return huespedes;
    }

    public void setHuespedes(Integer huespedes) {
        this.huespedes = huespedes;

    }public Boolean getPermiteFumar() {
        return permiteFumar;
    }

    public void setPermiteFumar(Boolean permiteFumar) {
        this.permiteFumar = permiteFumar;
    }
}
