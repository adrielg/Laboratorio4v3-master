package dam.isi.frsf.utn.edu.ar.laboratorio4v2.utils;

import java.util.List;


public interface BusquedaFinalizadaListener<T> {
    public void busquedaFinalizada(List<T> lRes);
    public void busquedaActualizada(String mensaje);
}
