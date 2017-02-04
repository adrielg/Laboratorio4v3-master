package dam.isi.frsf.utn.edu.ar.laboratorio4v2.utils;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import dam.isi.frsf.utn.edu.ar.laboratorio4v2.modelo.Departamento;
import dam.isi.frsf.utn.edu.ar.laboratorio4v2.modelo.Ciudad;

/*---AsyncTask<Params,Progress,Result--->*/
public class BuscarDepartamentosTask extends AsyncTask<FormBusqueda,Integer,List<Departamento>> {

    /*Creamos variable de tipo Interfaz, BusquedaFinalizadaListener*/
    private BusquedaFinalizadaListener<Departamento> listener;

    /*------------------------------------- Constructor ------------------------------------------*/
    public BuscarDepartamentosTask(BusquedaFinalizadaListener<Departamento> dListener){
        this.listener = dListener;
    }

    /*------------------------------------ On Pre Execute ----------------------------------------*/
    // Se ejecuta en el hilo principal antes que el hilo en Segundo plano de doInBackground() ejecute.
    // Se puede utilizar para inicialización de elementos necesarios en el proceso.
    protected void onPreExecute() {
        listener.busquedaActualizada("Vamos a comenzar a procesar");    //AGREGADO
        super.onPreExecute();
    }

    /*----------------------------------- On Post Execute ----------------------------------------*/
    // Es invocado desde el hilo de la UI luego de que doInBackground() finalize su ejecución, es decir, cuando finalice nuestra tarea.
    // Recibe como parametro el valor retornado por doInBackground() / por ejemplo un flag que indica exito o fracaso.

    /*---void onPostExecute(Result result)---*/
    protected void onPostExecute(List<Departamento> departamentos) {
        listener.busquedaActualizada("Busqueda Finalizada");    //AGREGADO
        listener.busquedaFinalizada(departamentos);             //AGREGADO
    }

    /*---------------------------------- On Progress Update --------------------------------------*/
    // Es invocado en el hilo principal de la UI y puede mostrarle al usuario el progreso de la operación.
    // Recibe como argumento los datos del resultado de doInBackground()
    // Se ejecutará cada vez que llamemos al método publishProgress() desde el método doInBackground()
    protected void onProgressUpdate(Integer... values) {
        listener.busquedaActualizada("departamento " + values[0]);
    }

    /*------------------------------------- On Cancelled -----------------------------------------*/
    //Se ejecutará cuando se cancele la ejecución de la tarea antes de su finalización normal.
    protected void onCancelled() {
        listener.busquedaActualizada("Búsqueda Cancelada");
    }

    /*---------------------------------- Do In Background ----------------------------------------*/
    // Código principal de nuestra tarea que recibe 3 parámetros (c/u un arreglo de var args) que se corresponden con los tipos definidos en AsyncTask.
    // Se ejecuta en un hilo secundario.
    // Debe retornar un valor que indique el resultado de la operación (de tipo generic), similar a los var args que recibe como argumento.

    /*---abstract Result doInBackground(Params... params)---*/
    protected List<Departamento> doInBackground(FormBusqueda... busqueda) {

        List<Departamento> todos = Departamento.getAlojamientosDisponibles();
        List<Departamento> resultado = new ArrayList<>();

        /*Tomo el departamento y saco sus datos*/
        Ciudad ciudadBuscada = busqueda[0].getCiudad();
        Integer cantHuespedes = busqueda[0].getHuespedes();     //AGREGADO
        Boolean aptoFumadores = busqueda[0].getPermiteFumar();  //AGREGADO
        Double precioMin = busqueda[0].getPrecioMinimo();       //AGREGADO
        Double precioMax = busqueda[0].getPrecioMaximo();       //AGREGADO

        // buscar todos los departamentos del sistema e ir chequeando las condiciones 1 a 1.
        // si cumplen las condiciones agregarlo a los resultados.

        Departamento depto;                     //AGREGADO
        int total_deptos, encontrado;

        total_deptos = todos.size();        //AGREGADO
        encontrado=0;

        for(int i = 0; i < total_deptos; i++) {
            depto = todos.get(i);
            if(depto.getCiudad().equals(ciudadBuscada)){ //Si es la Ciudad solicitada
                if (depto.getCapacidadMaxima() >= cantHuespedes){ //Si dispone capacidad de cantidad de huéspedes
                    if(!(aptoFumadores.equals(depto.getNoFumador()))){   //Si es o no fumador
                        if(depto.getPrecio() <= precioMax){// No aplico descuentos, porque en el adaptador tampoco figuran. Sino simplemente sería sacar el cálculo
                            if (depto.getPrecio() >= precioMin){
                                resultado.add(depto);
                                encontrado++;
                            }
                        }
                    }
                }
            }
            publishProgress(encontrado);                                     //AGREGADO
        }
        return resultado;
    }
}

/*
Estos métodos tienen una particularidad esencial para nuestros intereses. El método doInBackground() se ejecuta en un hilo secundario
(por tanto no podremos interactuar con la interfaz), pero sin embargo todos los demás se ejecutan en el hilo principal, lo que quiere
decir que dentro de ellos podremos hacer referencia directa a nuestros controles de usuario para actualizar la interfaz. Por su parte,
dentro de doInBackground() tendremos la posibilidad de llamar periódicamente al método publishProgress() para que automáticamente
desde el método onProgressUpdate() se actualice la interfaz si es necesario.

*/