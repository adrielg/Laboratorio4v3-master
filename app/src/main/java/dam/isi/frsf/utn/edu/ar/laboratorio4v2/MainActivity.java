package dam.isi.frsf.utn.edu.ar.laboratorio4v2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;  //AGREGADA
import android.view.MenuItem;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Arrays;

import android.widget.Toast;

import org.w3c.dom.Text;

import dam.isi.frsf.utn.edu.ar.laboratorio4v2.modelo.Ciudad;
import dam.isi.frsf.utn.edu.ar.laboratorio4v2.modelo.Usuario;
import dam.isi.frsf.utn.edu.ar.laboratorio4v2.utils.FormBusqueda;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FormBusqueda frmBusq;
    private SeekBar skPrecioMin;
    private SeekBar skPrecioMax;
    private TextView tvPrecioMinimo;
    private TextView tvPrecioMaximo;
    private Spinner cmbCiudad;
    private ArrayAdapter<Ciudad> adapterCiudad;
    private EditText txtHuespedes;
    private Switch swFumadores;
    private Button btnBuscar;
    public static Usuario usuario;
    private TextView textUsuario;
    private TextView textEmail;
    private String nombreUsuario;
    private String correoUsuario;
    private String nameRingtone;

    /*------------------------------------- ON CREATE --------------------------------------------*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Captura de los valores seteados en la actividad OpcionesActivity que extiende de PreferenceActivity*/ // AGREGADO
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String strRingtonePreference = prefs.getString("ringtonePref", "DEFAULT_RINGTONE_URI");
        nombreUsuario = prefs.getString("nombre_usuario","");
        correoUsuario = prefs.getString("email_usuario","");
        Uri ringtoneUri = Uri.parse(strRingtonePreference);

        /*Muestro que tengo cargado el Ringston que eligio en Configuraciones...*/ // AGREGADO
        Ringtone ringtone = RingtoneManager.getRingtone(getBaseContext(), ringtoneUri);
        nameRingtone = ringtone.getTitle(getBaseContext());

        /*Creamos el usuario*/
        usuario = new Usuario(nombreUsuario,correoUsuario,ringtoneUri);//Linea AGREGADA

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); //Crea una nueva variable Toolbar tomando como referencia la definida en app_bar_main.xml
        setSupportActionBar(toolbar); // indica a Android que vamos a utilizar nuestra propia Toolbar.

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout); //Crea una nueva variable DrawerLayout (contenedor de nuestro "contenido Principal" y de nuestro "navegador"), el activity_main.xml

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){//Compatibiliza el Drawer Layout con la barra de acciones (Toolbar)
        /*******************Agregado*******************/
           public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle("Después de cerrar el Navegador (ManiActivity)");//cambia el título a la toolbar
                invalidateOptionsMenu(); //indica a Android que los contenidos del menú han cambiado y que el menú debe ser redibujado. Crea una llamada al método onPrepareOptionsMenú
            }

           public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Antes de abrir el Navegador (ManiActivity)"); //cambia el título a la toolbar
                invalidateOptionsMenu(); //indica a Android que los contenidos del menú han cambiado y que el menú debe ser redibujado. Crea una llamada al método onPrepareOptionsMenú
            }
        /****************Fin de Agregado****************/
        };
        //drawer.setDrawerListener(toggle); //le setea un listenner al DrawerLayout //línea ORIGINAL pero método deprecated => cambié por addDrawerListener
        drawer.addDrawerListener(toggle); //le setea un listenner al DrawerLayout   // línea AGREGADA
        toggle.syncState(); //Sirve para sincronizar el "Indicador" (las tres barritas horizontales que despliega al navegador) del Drawer Layout
        toggle.setDrawerIndicatorEnabled(true); // setea que el Indicador esté visible // línea AGREGADA

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view); //Crea una nueva variable NavigationView, tomando como referencia la definida en activity_main.xml.
        navigationView.setNavigationItemSelectedListener(this); //le setea un listener
        View header=navigationView.getHeaderView(0);

        textUsuario = (TextView)header.findViewById(R.id.textView1);
        textEmail = (TextView)header.findViewById(R.id.textView2);
        textUsuario.setText(nombreUsuario);
        textEmail.setText(correoUsuario);

        frmBusq= new FormBusqueda(); //Crea un formulario con los criterios de búsqueda, vacío.

        frmBusq.setCiudad(Ciudad.CIUDADES[0]);  //Paris //AGREGADO

        tvPrecioMinimo = (TextView ) findViewById(R.id.txtPrecioMin);
        skPrecioMin = (SeekBar) findViewById(R.id.precioMin);
        skPrecioMin.setOnSeekBarChangeListener(listenerSB);

        tvPrecioMaximo= (TextView ) findViewById(R.id.txtPrecioMax);
        skPrecioMax= (SeekBar) findViewById(R.id.precioMax);
        skPrecioMax.setOnSeekBarChangeListener(listenerSB);

        adapterCiudad = new ArrayAdapter<Ciudad>(MainActivity.this,android.R.layout.simple_spinner_item, Arrays.asList(Ciudad.CIUDADES));
        cmbCiudad = (Spinner) findViewById(R.id.comboCiudad);
        cmbCiudad.setAdapter(adapterCiudad);
        cmbCiudad.setOnItemSelectedListener(comboListener);

        txtHuespedes = (EditText) findViewById(R.id.cantHuespedes);

        swFumadores = (Switch) findViewById(R.id.aptoFumadores);

        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(btnBusarListener);
    }//Fin ON CREATE

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(getBaseContext(), "OnResume...", Toast.LENGTH_SHORT).show();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String strRingtonePreference = prefs.getString("ringtonePref", "DEFAULT_RINGTONE_URI");
        nombreUsuario = prefs.getString("nombre_usuario","");
        correoUsuario = prefs.getString("email_usuario","");
        Uri ringtoneUri = Uri.parse(strRingtonePreference);

        /*Muestro que tengo cargado el Ringston que eligio en Configuraciones...*/ // AGREGADO
        Ringtone ringtone = RingtoneManager.getRingtone(getBaseContext(), ringtoneUri);
        nameRingtone = ringtone.getTitle(getBaseContext());
        //Toast.makeText(getBaseContext(), "Configuración cargada para nombre "+nombreUsuario+", correo: "+correoUsuario+", ringston: "+nameRingtone , Toast.LENGTH_SHORT).show();

        /*Seteamos Datos del usuario*/
        usuario.setNombre(nombreUsuario);
        usuario.setCorreo(correoUsuario);

        /*Seteo nuevamente los datos del usuario, por si se cambio las configuraciones*/
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view); //Crea una nueva variable NavigationView, tomando como referencia la definida en activity_main.xml.
        navigationView.setNavigationItemSelectedListener(this); //le setea un listener
        View header=navigationView.getHeaderView(0);

        textUsuario = (TextView)header.findViewById(R.id.textView1);
        textEmail = (TextView)header.findViewById(R.id.textView2);
        textUsuario.setText(nombreUsuario);
        textEmail.setText(correoUsuario);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Toast.makeText(getBaseContext(), "OnStart...", Toast.LENGTH_SHORT).show();
    }


    /*--------------------------------------------------------------------------------------------*/
    /*------------------------------ On Seek Bar Change Listener SeekBar "Precios"----------------*/
    private SeekBar.OnSeekBarChangeListener listenerSB =  new SeekBar.OnSeekBarChangeListener(){

        /*----------------------------------------*/
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(seekBar.getId()==R.id.precioMin) {
                tvPrecioMinimo.setText("Precio Mínimo "+progress);
                frmBusq.setPrecioMinimo(Double.valueOf(progress));
            }
            if(seekBar.getId()==R.id.precioMax) {
                tvPrecioMaximo.setText("Precio Máximo "+progress);
                frmBusq.setPrecioMaximo(Double.valueOf(progress));
            }
        }
         /* El usuario inicia la interacción con la Seekbar. */
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }
        /* El usuario realiza un stop en la interacción con la Seekbar. */
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    /*--------------------------------------------------------------------------------------------*/
    /*-------------------------------- On Item Selected Listener Spinner "Ciudad"-----------------*/
    private AdapterView.OnItemSelectedListener comboListener = new  AdapterView.OnItemSelectedListener(){

        /*Si selecciona una ciudad del spinner, la guardo*/
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            Ciudad item = (Ciudad) parent.getItemAtPosition(pos);
            frmBusq.setCiudad(item);
            Log.d("MainActivity","ciudad seteada "+item);
        }
        /*Si sale del spinner sin haber seleccionado alguna ciudad*/
        public void onNothingSelected(AdapterView<?> parent) {
            Toast.makeText(getBaseContext(), "Debe seleccionar una ciudad", Toast.LENGTH_LONG).show();
        }
    };

    /*--------------------------------------------------------------------------------------------*/
    /*------------------------------------ On Click Listener Button "Buscar" ---------------------*/
    private View.OnClickListener btnBusarListener = new View.OnClickListener() {

        public void onClick(View view) {

            Toast.makeText(getBaseContext(), "Clickee BUSCAR", Toast.LENGTH_LONG).show();

            if (TextUtils.isEmpty(txtHuespedes.getText().toString())){               //AGREGADO
                Toast.makeText(getBaseContext(), "Ingrese Cantidad de Huéspedes", Toast.LENGTH_LONG).show();
                txtHuespedes.requestFocus();
            }
            else{
                frmBusq.setHuespedes(Integer.parseInt(txtHuespedes.getText().toString()));  //AGREGADO
                frmBusq.setPermiteFumar(swFumadores.isChecked());

                Intent i = new Intent(MainActivity.this,ListaDepartamentosActivity.class);
                i.putExtra("esBusqueda",true);
                i.putExtra("frmBusqueda",frmBusq);
                startActivity(i);   // startActivityForResult(tarea,0); Otra forma que usé en el labo 3
            }
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //main.xml
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //Si selecciono CONFIGURACION
        if (id == R.id.action_settings) {
            Toast.makeText(getBaseContext(), "Clickee CONFIGURACION", Toast.LENGTH_LONG).show();
            //Inicio la actividad que me va a levantar el Preferencias.xml
            startActivity(new Intent(MainActivity.this,OpcionesActivity.class));
            return true;
        }
        //Si selecciono AYUDA
        if (id == R.id.ayuda) {
            Toast.makeText(getBaseContext(), "Clickee AYUDA", Toast.LENGTH_LONG).show();
            //Inicio la actividad que me va a levantar el Preferencias.xml
            startActivity(new Intent(MainActivity.this,AyudaActivity.class));
            return true;
        }
        //Si selecciono SALIR
        if (id == R.id.salir) {
            Toast.makeText(getBaseContext(), "Clickee SALIR", Toast.LENGTH_LONG).show();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*---------------------- on Navigation Item Selected (listener del Navegador)-----------------*/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.nav_deptos:
                Toast.makeText(getBaseContext(), "Clickee DEPARTAMENTOS", Toast.LENGTH_LONG).show();
                Intent i1 = new Intent(MainActivity.this,ListaDepartamentosActivity.class);
                i1.putExtra("esBusqueda",false );
                startActivity(i1);
                break;
            case R.id.nav_ofertas:
                Toast.makeText(getBaseContext(), "Clickee OFERTAS", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_destinos:
                Toast.makeText(getBaseContext(), "Clickee DESTINOS", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_reservas:
                if(usuario.getReservas().size()==0){
                    Toast.makeText(MainActivity.this,"No hay reserva",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent i2 = new Intent(MainActivity.this, AltaReservaActivity.class);
                    i2.putExtra("esReserva", false);
                    startActivity(i2);
                }
                break;
            case R.id.nav_perfil:
                Toast.makeText(getBaseContext(), "Clickee PERFIL", Toast.LENGTH_LONG).show();
                break;

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);//Cierra el NavigationView
        return true;//Devuelve TRUE en caso de que fue usado y FALSO en caso contrario
    }

    /*---------------------- on Back Pressed (botón retroceso del celular)------------------------*/
    //Al presionar el botón de volver atras del celular, lo primero que hace es cerrar el Navegador si estaba abierto
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
 //CODIGO AGREGADO MIO

 /*   private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

*/
