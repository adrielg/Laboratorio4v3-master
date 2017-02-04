package dam.isi.frsf.utn.edu.ar.laboratorio4v2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import dam.isi.frsf.utn.edu.ar.laboratorio4v2.modelo.Reserva;
import dam.isi.frsf.utn.edu.ar.laboratorio4v2.utils.BuscarDepartamentosTask;
import dam.isi.frsf.utn.edu.ar.laboratorio4v2.utils.BusquedaFinalizadaListener;
import dam.isi.frsf.utn.edu.ar.laboratorio4v2.utils.FormBusqueda;
import dam.isi.frsf.utn.edu.ar.laboratorio4v2.modelo.Departamento;


public class ListaDepartamentosActivity extends AppCompatActivity implements BusquedaFinalizadaListener<Departamento> {

    private TextView tvEstadoBusqueda;
    private ListView listaAlojamientos;
    private DepartamentoAdapter departamentosAdapter;
    private List<Departamento> lista;
    private Departamento itemValue;

    /*---------------------------------------- On Create -----------------------------------------*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alojamientos);
        lista = new ArrayList<>();
        listaAlojamientos= (ListView) findViewById(R.id.listaAlojamientos);
        tvEstadoBusqueda = (TextView) findViewById(R.id.estadoBusqueda);
    }

    /*---------------------------------------- On Start ------------------------------------------*/
    /*onStart()->Llamado cuando la actividad se está convirtiendo en visible para el usuario .*/
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        Boolean esBusqueda = intent.getExtras().getBoolean("esBusqueda");
        if(esBusqueda){
            FormBusqueda fb = (FormBusqueda ) intent.getSerializableExtra("frmBusqueda");//obtiene el formulario con los criterios seleccionados para la búsqueda
            new BuscarDepartamentosTask(ListaDepartamentosActivity.this).execute(fb);   //invoca una nueva tarea asincrónica (AsyncTask) para que procese la selección y manda a Ejecutar la tarea
            tvEstadoBusqueda.setText("Buscando....");                                   //donde el hilo principal para dicha tarea es el contexto de "ListaDepartamentosActivity"
            tvEstadoBusqueda.setVisibility(View.VISIBLE);//Lo haria dos veces- la 1era en busquedaFinalizada()
        }else{
            //Si presiono en la opcion del Menu, DEPARTAMENTOS
            tvEstadoBusqueda.setVisibility(View.GONE);
            lista = Departamento.getAlojamientosDisponibles();

        }
        //Carga el listView de la opcion del Menu, DEPARTAMENTOS
        departamentosAdapter = new DepartamentoAdapter(ListaDepartamentosActivity.this,lista);
        listaAlojamientos.setAdapter(departamentosAdapter);

    }

    /*----------------------------------- Búsqueda Finalizada ------------------------------------*/
    public void busquedaFinalizada(final List<Departamento> listaDepartamento) {
        tvEstadoBusqueda.setText("Búsqueda finalizada, departamentos encontrados: "+listaDepartamento.size()); //AGREGADO
        tvEstadoBusqueda.setVisibility(View.VISIBLE);                  //AGREGADO

        lista = listaDepartamento;                                  //AGREGADO
        departamentosAdapter = new DepartamentoAdapter(ListaDepartamentosActivity.this,lista);  //AGREGADO
        listaAlojamientos.setAdapter(departamentosAdapter);         //AGREGADO

        departamentosAdapter.notifyDataSetChanged();//Se notifica al adaptador de que el ArrayList que tiene asociado ha sufrido cambios (forzando asi a ir al metodo getView()) //AGREGADO

        listaAlojamientos.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // La posición donde se hace clic en el elemento de lista se obtiene de la
                // la posición de parámetro de la vista de lista de Android
                view.requestFocus();
                itemValue = (Departamento) listaAlojamientos.getItemAtPosition(position); //obtengo el departamento donde se hizo clic

                /*Dialogo de Alerta, Confirmación de la Reserva*/
                mostrarDialogo(itemValue);
            }
        });
        //---------------------Fin Selección Item Departamento-------------//
    }

    /*---------------------------------- Búsqueda Actualizada ------------------------------------*/
    public void busquedaActualizada(String msg) {
        tvEstadoBusqueda.setText(" Buscando... "+msg);
    }

    public void mostrarDialogo(final Departamento itemValue){
        AlertDialog.Builder builder = new AlertDialog.Builder(ListaDepartamentosActivity.this);
        builder.setTitle("Atención")
                .setMessage("¿Deseas reservar este departamento?"+"\n\n"+
                        "Precio: "+String.format( "$ %.2f", itemValue.getPrecio())+"\nDescripción: "+itemValue.getDescripcion()+"\nCiudad: "+itemValue.getCiudad())
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Aquí lo que deseas realizar
                        Intent i = new Intent(ListaDepartamentosActivity.this,AltaReservaActivity.class);
                        i.putExtra("listaReservas",(ArrayList<Reserva>) itemValue.getReservas());
                        i.putExtra("esReserva",true);

                        startActivity(i);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}


