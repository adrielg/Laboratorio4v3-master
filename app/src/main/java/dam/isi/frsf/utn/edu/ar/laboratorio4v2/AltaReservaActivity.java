package dam.isi.frsf.utn.edu.ar.laboratorio4v2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import dam.isi.frsf.utn.edu.ar.laboratorio4v2.modelo.Reserva;

public class AltaReservaActivity extends AppCompatActivity {

    private ListView listaReserva;
    private ReservaAdapter reservaAdapter;
    private Button btnReservar;
    public static Reserva seleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_reserva);

        listaReserva = (ListView) findViewById(R.id.listaReservas);
        btnReservar = (Button) findViewById(R.id.btnReservar);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final Intent intent = getIntent();
        Boolean esReserva = intent.getExtras().getBoolean("esReserva");

        btnReservar.setVisibility(View.INVISIBLE); //Solamente VISIBLE a la hora de querer hacer la Reserva

        if (esReserva){
            ArrayList<Reserva> reserva = (ArrayList<Reserva>) intent.getSerializableExtra("listaReservas");

            reservaAdapter = new ReservaAdapter(AltaReservaActivity.this, reserva);
            listaReserva.setAdapter(reservaAdapter);

            listaReserva.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    view.setSelected(true);
                    seleccionado = (Reserva) listaReserva.getItemAtPosition(position);
                }
            });

            reservaAdapter.notifyDataSetChanged();//Se notifica al adaptador de que el ArrayList que tiene asociado ha sufrido cambios (forzando asi a ir al metodo getView()) //AGREGADO

            btnReservar.setVisibility(View.VISIBLE);
            btnReservar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (seleccionado == (null)) {
                        Toast.makeText(AltaReservaActivity.this, "¡Debe Seleccionar una reserva!", Toast.LENGTH_SHORT).show();
                    } else {
                       enviarNotificacion();
                    }
                }
            });
        }
        else {
            //Si presiono en la opcion del Menu, RESERVAS
            ArrayList<Reserva> reserva = (ArrayList<Reserva>) intent.getSerializableExtra("listaReservas");
            reservaAdapter = new ReservaAdapter(AltaReservaActivity.this, reserva);
            listaReserva.setAdapter(reservaAdapter);
            reservaAdapter.notifyDataSetChanged();
            //el Boton RESERVAS no va a aparecer..
        }
    }

    private void enviarNotificacion(){
        Toast.makeText(AltaReservaActivity.this,"La reserva está en pendiente",Toast.LENGTH_LONG).show();
        PendientesReceiver activarAlarma = new PendientesReceiver();
        activarAlarma.sendRepeatingAlarm(AltaReservaActivity.this);
        finish();
    }
}